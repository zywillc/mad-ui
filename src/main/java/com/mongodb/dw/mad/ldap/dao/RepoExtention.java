package com.mongodb.dw.mad.ldap.dao;

import com.mongodb.dw.mad.ldap.entity.LdapDwDsEntry;

import java.util.List;

public interface RepoExtention {
    void create(LdapDwDsEntry ldapDwDsEntry);
}
