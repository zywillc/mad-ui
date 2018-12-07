package com.mongodb.dw.mad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@SpringBootApplication
@EnableLdapRepositories(basePackages = "com.mongodb.dw.ldap.**")
@EnableConfigurationProperties(ClusterProperties.class)
public class MadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MadApplication.class, args);
	}
}
