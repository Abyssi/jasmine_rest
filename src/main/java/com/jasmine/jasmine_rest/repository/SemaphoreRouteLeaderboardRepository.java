package com.jasmine.jasmine_rest.repository;

import com.jasmine.jasmine_rest.Models.Mongo.SemaphoreRouteLeaderboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemaphoreRouteLeaderboardRepository extends MongoRepository<SemaphoreRouteLeaderboard, String> {
    boolean existsByTimeWindowMilliseconds(Integer timeWindowMilliseconds);

    SemaphoreRouteLeaderboard findByTimeWindowMilliseconds(Integer timeWindowMilliseconds);
}
