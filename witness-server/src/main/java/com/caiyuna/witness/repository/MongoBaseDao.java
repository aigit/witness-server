/**
 * 
 */
package com.caiyuna.witness.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Component
public class MongoBaseDao {

    private final MongoTemplate mongoTemplate;

    /**
     * 构造函数
     */
    @Autowired
    public MongoBaseDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected <T> void save(T t, String collectionName) {
        mongoTemplate.save(t, collectionName);
    }

    protected <T> T findEntityById(Class<T> c, String id, String collectionName) {
        return mongoTemplate.findById(id, c, collectionName);
    }

}
