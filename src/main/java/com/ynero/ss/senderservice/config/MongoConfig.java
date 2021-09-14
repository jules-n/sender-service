package com.ynero.ss.senderservice.config;


import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.MongoDBConfiguration;
import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongock
@EnableMongoAuditing
@Import(MongoDBConfiguration.class)
public class MongoConfig {
}