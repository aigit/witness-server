/**
 * 
 */
package com.caiyuna.witness.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.caiyuna.witness.entity.Scene;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Component
public class SceneDao extends MongoBaseDao {

    private static final String COLLECTION_NAME = "w_scene";

    /**
     * 构造函数
     * @param mongoTemplate
     */
    public SceneDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    /**
     * @Author Ldl
     * @Date 2018年1月24日
     * @since 1.0.0
     * @param t
     * @param collectionName
     * @see com.caiyuna.witness.repository.MongoBaseDao#save(java.lang.Object, java.lang.String)
     */
    public void save(Scene scene) {
        super.save(scene, COLLECTION_NAME);
    }

}
