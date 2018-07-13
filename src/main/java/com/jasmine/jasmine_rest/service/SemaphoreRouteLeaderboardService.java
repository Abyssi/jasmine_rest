package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.Models.Mongo.SemaphoreRouteLeaderboard;
import com.jasmine.jasmine_rest.repository.SemaphoreRouteLeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SemaphoreRouteLeaderboardService {

    @Autowired
    private SemaphoreRouteLeaderboardRepository semaphoreRouteLeaderboardRepository;

    @Transactional
    public SemaphoreRouteLeaderboard save(SemaphoreRouteLeaderboard semaphoreRouteLeaderboard) {
        if (semaphoreRouteLeaderboardRepository.existsByTimeWindowMilliseconds(semaphoreRouteLeaderboard.getTimeWindowMilliseconds()))
            semaphoreRouteLeaderboard.setId(semaphoreRouteLeaderboardRepository.findByTimeWindowMilliseconds(semaphoreRouteLeaderboard.getTimeWindowMilliseconds()).getId());

        return semaphoreRouteLeaderboardRepository.save(semaphoreRouteLeaderboard);
    }

    @Transactional
    public SemaphoreRouteLeaderboard findByTimeWindowMilliseconds(Integer timeWindowMilliseconds) {
        return semaphoreRouteLeaderboardRepository.findByTimeWindowMilliseconds(timeWindowMilliseconds);
    }
}
