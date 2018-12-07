package com.mongodb.dw.mad.utils;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StrSubstitutor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.HashMap;
import java.util.Map;

public class LdapUtils {

    public final static String BASE = "ou=dw-groups,dc=mongodb,dc=com";

    private LdapUtils() {}

    public final static String getNamespace(final String database, final String collection){
        String namespace = StringUtils.joinWith(".", database, collection);
        return namespace;
    }

    public final static String generateMadName(final String cluster, final String accessType, final String db, final String coll){
        Map<String, String> map = new HashMap<>();
        map.put("cl", cluster);
        map.put("ns", getNamespace(db, coll));
        map.put("at", accessType);
        String templateString = "cl=${cl},ns=${ns},at=${at}";
        StrSubstitutor sub = new StrSubstitutor(map);
        String resolvedString = sub.replace(templateString);
        return resolvedString;
    }

    public final static String generateLdapName(final String cluster, final String accessType, final String db, final String coll){
        String madName = generateMadName(cluster, accessType, db, coll);
        return StringEscapeUtils.escapeJava(madName);
    }


    public final static Name generateDn(final String cluster, final String accessType, final String db, final String coll,
                                        final String base){
        Name dn = LdapNameBuilder
                .newInstance(base)
                .add("cn", generateLdapName(cluster, accessType, db, coll))
                .build();
        return dn;
    }
}
