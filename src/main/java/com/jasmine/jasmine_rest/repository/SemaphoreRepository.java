package com.jasmine.jasmine_rest.repository;

import com.jasmine.jasmine_rest.Models.JNLightBulbStatus;
import com.jasmine.jasmine_rest.Models.Mongo.Semaphore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemaphoreRepository extends MongoRepository<Semaphore, String> {
    List<Semaphore> findAllByLocationWithin(Box cell);

    List<Semaphore> findAllByLightBulbs_Status(JNLightBulbStatus status);

    Page<Semaphore> findAllByLightBulbs_Status(JNLightBulbStatus status, Pageable pageable);

    Semaphore findByCrossroads_IdAndSemaphoreId(String crossroadsId, String semaphoreId);

    Page<Semaphore> findAll(Pageable pageable);
}
