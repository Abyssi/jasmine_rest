package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.Models.Mongo.CrossroadsLeaderboard;
import com.jasmine.jasmine_rest.repository.CrossroadsLeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrossroadsLeaderboardService {

    @Autowired
    private CrossroadsLeaderboardRepository crossroadsLeaderboardRepository;

    @Transactional
    public CrossroadsLeaderboard save(CrossroadsLeaderboard crossroadsLeaderboard) {
        if (crossroadsLeaderboardRepository.existsByTimeWindowMilliseconds(crossroadsLeaderboard.getTimeWindowMilliseconds()))
            crossroadsLeaderboard.setId(crossroadsLeaderboardRepository.findByTimeWindowMilliseconds(crossroadsLeaderboard.getTimeWindowMilliseconds()).getId());

        return crossroadsLeaderboardRepository.save(crossroadsLeaderboard);
    }

    @Transactional
    public CrossroadsLeaderboard findByTimeWindowMilliseconds(Integer timeWindowMilliseconds) {
        return crossroadsLeaderboardRepository.findByTimeWindowMilliseconds(timeWindowMilliseconds);
    }
}
