package com.mongodb.dw.mad.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.mongodb.dw.mad.ClusterProperties;
import com.mongodb.dw.mad.pojos.ApiResObject;
import com.mongodb.dw.mad.pojos.DbUser;
import com.mongodb.dw.mad.pojos.MadDTO;
import com.mongodb.dw.mad.pojos.MadListDTO;
import com.mongodb.dw.mad.ldap.dao.LdapDwDsRepo;
import com.mongodb.dw.mad.ldap.entity.LdapDwDsEntry;
import com.mongodb.dw.mad.mongo.dao.MadRepo;
import com.mongodb.dw.mad.mongo.entity.Mad;
import com.mongodb.dw.mad.pojos.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import javax.naming.Name;

import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mongodb.dw.mad.utils.LdapUtils.*;

@Slf4j
@Service
public class MadService {
    @Autowired
    private MadRepo madRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LdapDwDsRepo ldapDwDsRepo;

    @Autowired
    private static ClusterProperties cp;
    private static HttpHeaders headers = new HttpHeaders();

    static {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Value("${atlas.api_base_url: https://cloud.mongodb.com/api/atlas/v1.0}")
    private String BaseUrl;

    public List<Mad> getAllMad() {
        List<Mad> mads = madRepo.findAll();
        return mads;
    }


    public void deleteMad(String cl, String db, String coll, String at) {
         String madId = generateMadName(cl, at, db, coll);
         deleteMadById(madId);
    }

    public void deleteMadById(String id) {
        madRepo.deleteById(id);
        log.debug("_id: {} was deleted from Mongo", id);
    }

    public void deleteLdap(String cl, String db, String coll, String at) {
        Name ldapId = generateDn(cl, at, db, coll, BASE);
        ldapDwDsRepo.deleteById(ldapId);
        log.debug("dn: {} was deleted from LDAP", ldapId);

    }

    public void deleteLdapByMadId(String id) {
        ldapDwDsRepo.deleteById(convertMadIdToLdapDn(id));
        log.debug("_id: {} was deleted from LDAP", id);
    }

    public void upsertAccess(MadDTO madDTO) {
        Name dn = createLdapDwDs(madDTO);
        String cluster = madDTO.getCluster();
        List<String> users = getUserNames(cluster);
        URI uri = getApiUri(cluster);

        Mad mad = convertToMad(madDTO);
        JsonNode node = generateJsonNode(mad);
        HttpEntity<JsonNode> request = new HttpEntity<>(node, headers);

        if(users.contains(dn.toString())) {
            ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.PATCH,
                    request, String.class);
            log.debug("updated role: \n {}",resp.getBody());
        } else {
            ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.POST,
                    request, String.class);
            log.debug("created role: \n {}", resp.getBody());
        }
    }

    public void saveMadToMongo(MadDTO madDTO) {
        Mad mad = convertToMad(madDTO);
        madRepo.save(mad);
    }

    public void saveMadListToMongo(MadListDTO madListDTO) {
        List<MadDTO> madDTOList = madListDTO.getMads();
        madDTOList.parallelStream().forEach( s -> saveMadToMongo(s) );
    }

    public Name createLdapDwDs(MadDTO madDTO){
        String cluster = madDTO.getCluster();
        String db = madDTO.getDatabase();
        String coll = madDTO.getCollection();
        String accessType = madDTO.getAccessType();

        LdapDwDsEntry entry = LdapDwDsEntry.of(cluster, db, coll, accessType);
        Name id = generateDn(cluster, accessType, db, coll, BASE);
        entry.setId(id);

        String name = generateMadName(cluster, accessType, db, coll);
        String escapeName = generateLdapName(cluster, accessType, db, coll);
        entry.setName(escapeName);

        if(exists(escapeName)){
            throw new LdapEntryExistsException("cn: " + name + "has already existed");
        }
        ldapDwDsRepo.create(entry);
        return id;
    }

    public List<Name> batchCreateLadpDwDs(MadListDTO madListDTO) {
        List<MadDTO> madDTOList = madListDTO.getMads();
        return madDTOList.parallelStream().map( s -> createLdapDwDs(s)).collect(Collectors.toList());
    }


    public Boolean exists(final String name) {
        LdapDwDsEntry entry = ldapDwDsRepo.findByName(name);
        return entry != null;
    }



    /*
       private
    */
    private static class LdapEntryExistsException extends RuntimeException {
        public LdapEntryExistsException(String errorMessage) {
            super(errorMessage);
        }
    }

    private ApiResObject getApiResObject(URI uri){
        ResponseEntity<ApiResObject> resp = restTemplate.exchange(uri, HttpMethod.GET,
                new HttpEntity(headers), ApiResObject.class);
        return resp.getBody();
    }

    private URI getApiUri(String cluster) {
        String projectId = cp.getProjectId(cluster);
        String url = Paths.get(BaseUrl, "{projectId}", "databaseUsers").toString();
        URI uri = new UriTemplate(url).expand(projectId);
        return uri;
    }

    private List<String> getUserNames(String cluster){
        URI uri = getApiUri(cluster);
        ApiResObject resp = getApiResObject(uri);
        Stream<DbUser> users = resp.getLinks().stream()
                                              .map(l -> getApiResObject(URI.create(l.getHref())).getResults().stream())
                                              .reduce(Stream::concat).orElseGet(Stream::empty);

        return users.map(u -> u.getUsername()).collect(Collectors.toList());
    }

    private JsonNode generateJsonNode(Mad mad) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();
        Role role = Role.of(mad.getCollection(),mad.getDatabase(), mad.getAccessType());
        ((ObjectNode) node).set("roles", mapper.valueToTree(role));
        return node;
    }
    private static Mad convertToMad(MadDTO madDTO) {
        String at = madDTO.getAccessType();
        String cl = madDTO.getCluster();
        String db = madDTO.getDatabase();
        String coll = madDTO.getCollection();
        String id = generateMadName(cl, at, db, coll);
        Mad mad = Mad.builder()
                .id(id)
                .accesstype(at)
                .cluster(cl)
                .mongosrv(cp.getMongoSrv(cl))
                .database(db)
                .collection(coll)
                .title(madDTO.getTitle())
                .description(madDTO.getDescription())
                .tags(madDTO.getTags())
                .ldapGrop(id)
                .build();

        return mad;
    }

    private Name convertMadIdToLdapDn(String id) {
        Map<String, String> map = splitToMap(id);
        Name dn = generateDn(map.get("cl"), map.get("at"), map.get("db"), map.get("coll"), BASE);
        return dn;

    }
    private Map<String, String> splitToMap(String id) {
        return Splitter.on(",").withKeyValueSeparator("=").split(id);
    }

}
