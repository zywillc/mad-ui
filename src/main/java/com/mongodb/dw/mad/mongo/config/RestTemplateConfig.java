package com.mongodb.dw.mad.mongo.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.mongodb.dw.mad.mongo.client.HttpComponentsClientHttpRequestFactoryDigestAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Autowired
    private Environment env;

    public RestTemplateConfig() {
        super();
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpHost host = new HttpHost("cloud.mongodb.com", 443, "https");
        CloseableHttpClient client = HttpClientBuilder.create().
                setDefaultCredentialsProvider(provider()).useSystemProperties().build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactoryDigestAuth(host, client);

        return new RestTemplate(requestFactory);
    }

    private CredentialsProvider provider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        String user = env.getRequiredProperty("altas.api_user");
        String pass = env.getRequiredProperty("atlas.api_key");
        UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials(user, pass);
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }
}
