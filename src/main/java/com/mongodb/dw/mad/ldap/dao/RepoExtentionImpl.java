package com.mongodb.dw.mad.ldap.dao;

import com.mongodb.dw.mad.ldap.entity.LdapDwDsEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;

@Slf4j
public class RepoExtentionImpl implements RepoExtention{

    @Autowired
    private LdapTemplate ldapTemplate;
    @Override
    public void create(LdapDwDsEntry ldapDwDsEntry) {
        ldapTemplate.create(ldapDwDsEntry);
        log.info("group entry: %s \n is created successfully", ldapDwDsEntry);
    }

}
