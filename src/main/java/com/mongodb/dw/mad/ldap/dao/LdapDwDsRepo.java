package com.mongodb.dw.mad.ldap.dao;

import com.mongodb.dw.mad.ldap.entity.LdapDwDsEntry;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LdapDwDsRepo extends LdapRepository<LdapDwDsEntry>, RepoExtention{
    LdapDwDsEntry findByName(String name);

    List<LdapDwDsEntry> findAllByBase(String base);

    List<LdapDwDsEntry> findByNameLikeIgnoreCase(String regex);

    List<LdapDwDsEntry> findByIdLikeIgnoreCase(String regex);
}
