package com.jasmine.jasmine_rest.configuration;

import com.jasmine.jasmine_rest.service.cascade.CascadeSaveMongoEventListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    //@Value("${spring.data.mongodb.host}")
    //private String host;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.uri}")
    private String dbURI;

    @Bean
    public MongoTemplate mongoTemplate() {
        //return new MongoTemplate(new MongoClient(host), database);
        return new MongoTemplate(new MongoClient(new MongoClientURI(dbURI)), database);
    }

    @Bean
    public CascadeSaveMongoEventListener cascadingMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }
}
