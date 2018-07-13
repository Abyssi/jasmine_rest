package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.Models.Mongo.OutlierCrossroads;
import com.jasmine.jasmine_rest.repository.OutlierCrossroadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OutlierCrossroadsService {

    @Value("${kafka.small.window}")
    private Integer kafkaSmallWindow;

    @Value("${kafka.medium.window}")
    private Integer kafkaMediumWindow;

    @Value("${kafka.large.window}")
    private Integer kafkaLargeWindow;

    @Autowired
    private OutlierCrossroadsRepository outlierCrossroadsRepository;

    @Transactional
    public OutlierCrossroads save(OutlierCrossroads outlierCrossroads) {
        if (outlierCrossroadsRepository.existsByBaseIdAndTimeWindowMilliseconds(outlierCrossroads.getBase().getId(), outlierCrossroads.getTimeWindowMilliseconds()))
            outlierCrossroadsRepository.deleteByBaseIdAndTimeWindowMilliseconds(outlierCrossroads.getBase().getId(), outlierCrossroads.getTimeWindowMilliseconds());

        return outlierCrossroadsRepository.save(outlierCrossroads);
    }

    @Transactional
    public void deleterOlderByTimeWindowMilliseconds(Integer timeWindowMilliseconds) {
        outlierCrossroadsRepository.deleteAllByTimestampLessThanAndTimeWindowMilliseconds(Instant.now().minus(Duration.ofMillis(timeWindowMilliseconds)).toEpochMilli(), timeWindowMilliseconds);
    }

    @Transactional
    public List<OutlierCrossroads> findAllByTimeWindowMilliseconds(Integer timeWindowMilliseconds) {
        return outlierCrossroadsRepository.findAllByTimeWindowMilliseconds(timeWindowMilliseconds);
    }

    @Scheduled(cron = "* * * * * ?")
    public void purge() {
        //deleterOlderByTimeWindowMilliseconds(kafkaSmallWindow);
        //deleterOlderByTimeWindowMilliseconds(kafkaMediumWindow);
        //deleterOlderByTimeWindowMilliseconds(kafkaLargeWindow);
    }
}
