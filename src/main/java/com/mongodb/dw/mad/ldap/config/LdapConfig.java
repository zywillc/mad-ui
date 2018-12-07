package com.mongodb.dw.mad.ldap.config;
import com.mongodb.dw.mad.ldap.dao.RepoExtention;
import com.mongodb.dw.mad.ldap.dao.RepoExtentionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;

@Configuration
public class LdapConfig {
    @Autowired
    private Environment env;

    @Bean
    public ContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        contextSource.setBase(env.getRequiredProperty("ldap.base"));
        contextSource.setUserDn(env.getRequiredProperty("ldap.bind_dn"));
        contextSource.setPassword(env.getRequiredProperty("ldap.bind_password"));
        contextSource.afterPropertiesSet();

        PoolingContextSource poolingContextSource = new PoolingContextSource();
        poolingContextSource.setDirContextValidator(new DefaultDirContextValidator());
        poolingContextSource.setContextSource(contextSource);
        poolingContextSource.setTestOnBorrow(true);
        poolingContextSource.setTestWhileIdle(true);

        TransactionAwareContextSourceProxy proxy = new TransactionAwareContextSourceProxy(poolingContextSource);

        return proxy;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @Bean
    public RepoExtention repoExtention() {
        return new RepoExtentionImpl();
    }
}
