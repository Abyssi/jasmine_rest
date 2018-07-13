package com.jasmine.jasmine_rest.repository;

import com.jasmine.jasmine_rest.Models.Mongo.CrossroadsLeaderboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossroadsLeaderboardRepository extends MongoRepository<CrossroadsLeaderboard, String> {
    boolean existsByTimeWindowMilliseconds(Integer timeWindowMilliseconds);

    CrossroadsLeaderboard findByTimeWindowMilliseconds(Integer timeWindowMilliseconds);
}
