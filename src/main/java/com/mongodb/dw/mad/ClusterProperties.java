package com.mongodb.dw.mad;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource("classpath:cluster.properties")
@ConfigurationProperties()
public class ClusterProperties {
    private Map<String, String> mongosrv;
    private Map<String, String> projectid;

    public String getMongoSrv(String cluster) {
        return mongosrv.get(cluster);
    }

    public String getProjectId(String cluster) {
        return projectid.get(cluster);
    }
}
