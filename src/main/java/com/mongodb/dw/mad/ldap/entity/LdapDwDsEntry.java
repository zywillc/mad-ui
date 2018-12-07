package com.mongodb.dw.mad.ldap.entity;

import lombok.*;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;


import javax.naming.Name;

@Data
@ToString(includeFieldNames=true)
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Entry(objectClasses = {"mongodbGroup", "groupOfNames", "top"},  base = "ou=dw-groups,dc=mongodb,dc=com")
public class LdapDwDsEntry {

    @Id
    private Name id;

    @DnAttribute(value="ou")
    private String group;

    @Attribute(name = "cn")
    @DnAttribute(value = "cn", index=0)
    private String name;

    @Transient
    @NonNull private String accessType;

    @Transient
    @NonNull private String cluster;


    @Transient
    @NonNull private String database;

    @Transient
    @NonNull private String collection;

}
