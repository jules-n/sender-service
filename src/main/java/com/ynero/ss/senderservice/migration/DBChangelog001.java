package com.ynero.ss.senderservice.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;

@ChangeLog(order = "001")
@Log4j2
public class DBChangelog001 {

    @ChangeSet(order = "001", systemVersion = "001", id = "all collections", author = "ynero")
    public void createCollections(MongoDatabase mongo) {
        mongo.listCollectionNames().forEach(
                collection -> log.info("collection: {}", collection)
        );
    }
}
