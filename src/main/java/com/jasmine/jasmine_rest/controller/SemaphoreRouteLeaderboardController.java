package com.jasmine.jasmine_rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.Models.Mongo.SemaphoreRouteLeaderboard;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.response_entity.ResponseEntityBuilder;
import com.jasmine.jasmine_rest.service.SemaphoreRouteLeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/semaphore_route_leader_boards/")
public class SemaphoreRouteLeaderboardController {

    @Autowired
    private SemaphoreRouteLeaderboardService semaphoreRouteLeaderboardService;

    @JsonView(JsonViews.DetailedSemaphoreRouteLeaderboard.class)
    @RequestMapping(value = "{millis}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Integer millis) {
        SemaphoreRouteLeaderboard semaphoreRouteLeaderboard = semaphoreRouteLeaderboardService.findByTimeWindowMilliseconds(millis);

        return new ResponseEntityBuilder<>(semaphoreRouteLeaderboard).setStatus(HttpStatus.OK).build();
    }
}
