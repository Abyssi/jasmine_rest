package com.jasmine.jasmine_rest.repository;

import com.jasmine.jasmine_rest.Models.Mongo.OutlierCrossroads;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutlierCrossroadsRepository extends MongoRepository<OutlierCrossroads, String> {

    List<OutlierCrossroads> findAllByTimeWindowMilliseconds(Integer timeWindowMilliseconds);

    void deleteAllByTimestampLessThanAndTimeWindowMilliseconds(Long timestamp, Integer timeWindowMilliseconds);

    boolean existsByBaseIdAndTimeWindowMilliseconds(String id, Integer timeWindowMilliseconds);

    void deleteByBaseIdAndTimeWindowMilliseconds(String id, Integer timeWindowMilliseconds);
}
