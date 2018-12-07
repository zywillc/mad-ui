package com.mongodb.dw.mad.mongo.dao;

import com.mongodb.dw.mad.mongo.entity.Mad;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MadRepo extends MongoRepository<Mad, String> {
    List<Mad> findByAccessType(String accessType);

    List<Mad> findByTagsIn(List<String> tags);

    List<Mad> findByCluster(String cluster);

    List<Mad> findByClusterLike(String regex);
}
