package com.mongodb.dw.mad.controller;

import com.mongodb.dw.mad.mongo.entity.Mad;
import com.mongodb.dw.mad.pojos.MadDTO;
import com.mongodb.dw.mad.service.MadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MadController {

    @Autowired
    private MadService madService;


    @RequestMapping(value = "/ldaps", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FOUND)
    public List<Mad> getAllMad() {
        return madService.getAllMad();
    }

    @DeleteMapping(value = "/mongo", params = {"cl", "at", "db", "coll"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteMad(@RequestParam String cl, @RequestParam String at,
                          @RequestParam String db, @RequestParam String coll) {
        log.info("Request to delete Mad with cluster: {}, accessType: {}, namespace: {}.{}",
                cl, at, db, coll);
        madService.deleteMad(cl, at, db, coll);
    }

    @DeleteMapping(value = "/ldap", params = {"cl", "at", "db", "coll"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteLdap(@RequestParam String cl, @RequestParam String at,
                          @RequestParam String db, @RequestParam String coll) {
        log.info("Request to delete LDAP with cluster: {}, accessType: {}, namespace: {}.{}",
                cl, at, db, coll);
        madService.deleteLdap(cl, at, db, coll);
    }

    @DeleteMapping(value = "/ldap/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLdapById(@PathVariable String id) {
        log.info("Request to delete LDAP with id: {}", id);
        madService.deleteLdapByMadId(id);
    }

    @DeleteMapping(value = "/mongo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMadById(@PathVariable String id) {
        log.info("Request to delete LDAP with id: {}", id);
        madService.deleteMadById(id);
    }


    @RequestMapping(value = "/ldaps", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.CREATED)
    public MadDTO createToLdap(@Valid @RequestBody MadDTO madDTO) {
        madService.createLdapDwDs(madDTO);
        return madDTO;
    }


    @RequestMapping(value = "/mongo", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.CREATED)
    public MadDTO createToMongo(@Valid @RequestBody MadDTO madDTO) {
        madService.saveMadToMongo(madDTO);
        return madDTO;
    }

    @RequestMapping(value = "/access", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseStatus(HttpStatus.CREATED)
    public MadDTO upSertAccessToLdapAndAtlas(@Valid @RequestBody MadDTO madDTO) {
        madService.upsertAccess(madDTO);
        return madDTO;
    }



}
