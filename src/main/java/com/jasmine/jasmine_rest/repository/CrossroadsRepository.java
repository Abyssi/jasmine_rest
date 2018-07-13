package com.jasmine.jasmine_rest.repository;

import com.jasmine.jasmine_rest.Models.Mongo.Crossroads;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossroadsRepository extends MongoRepository<Crossroads, String> {
}
