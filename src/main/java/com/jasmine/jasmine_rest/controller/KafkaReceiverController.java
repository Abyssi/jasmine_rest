package com.jasmine.jasmine_rest.controller;

import com.jasmine.jasmine_rest.Models.*;
import com.jasmine.jasmine_rest.Models.Mongo.*;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.service.*;
import com.jasmine.jasmine_rest.utils.JSONSerializableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.stream.Collectors;

@Controller
public class KafkaReceiverController {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiverController.class);

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private CrossroadsLeaderboardService crossroadsLeaderboardService;

    @Autowired
    private SemaphoreRouteLeaderboardService semaphoreRouteLeaderboardService;

    @Autowired
    private OutlierCrossroadsService outlierCrossroadsService;

    @Autowired
    private SemaphoreService semaphoreService;

    @Autowired
    private CrossroadsService crossroadsService;

    @Value("${websocket.top.semaphore.route.window}")
    private Integer websocketTopSemaphoreRouteWindow;

    @Value("${websocket.small.window}")
    private Integer websocketSmallWindow;

    @Value("${websocket.medium.window}")
    private Integer websocketMediumWindow;

    @Value("${websocket.large.window}")
    private Integer websocketLargeWindow;

    @Value("${websocket.top.ten.crossroads.topic}${websocket.small.window}${websocket.topic.suffix}")
    private String websocketTopTenCrossroadsSmallWindowTopic;

    @Value("${websocket.top.ten.crossroads.topic}${websocket.medium.window}${websocket.topic.suffix}")
    private String websocketTopTenCrossroadsMediumWindowTopic;

    @Value("${websocket.top.ten.crossroads.topic}${websocket.large.window}${websocket.topic.suffix}")
    private String websocketTopTenCrossroadsLargeWindowTopic;

    @Value("${websocket.outlier.crossroads.topic}${websocket.small.window}${websocket.topic.suffix}")
    private String websocketOutliersCrossroadsSmallWindowTopic;

    @Value("${websocket.outlier.crossroads.topic}${websocket.medium.window}${websocket.topic.suffix}")
    private String websocketOutliersCrossroadsMediumWindowTopic;

    @Value("${websocket.outlier.crossroads.topic}${websocket.large.window}${websocket.topic.suffix}")
    private String websocketOutliersCrossroadsLargeWindowTopic;

    @Value("${websocket.damaged.semaphore.topic}${websocket.topic.suffix}")
    private String websocketDamagedSemaphoreTopic;

    @Value("${websocket.top.semaphore.route.topic}${websocket.topic.suffix}")
    private String websocketTopSemaphoreRouteTopic;

    @KafkaListener(topics = "${kafka.top.ten.crossroads.topic}${kafka.small.window}${kafka.topic.suffix}", containerFactory = "kafkaListenerCrossroadsListContainerFactory")
    public void topTenCrossroadsSmallWindowListen(@Payload JNCrossroadsList crossroadsList) {
        LOG.info("[IN][top-10-crossroads-small-window] '{}'", crossroadsList.toJSONString());

        CrossroadsLeaderboard crossroadsLeaderboard = crossroadsLeaderboardService.save(new CrossroadsLeaderboard(crossroadsList.stream().map(crossroads ->
                new RichCrossroads(crossroadsService.findById(crossroads.getId()).get(), crossroads.getAverageSpeed(), crossroads.getMedianVehiclesCount())
        ).collect(Collectors.toList()), websocketSmallWindow));

        messagingService.sendMessage(crossroadsLeaderboard, JsonViews.DetailedCrossroadsLeaderboard.class, websocketTopTenCrossroadsSmallWindowTopic);
        LOG.info("[OUT][top-10-crossroads-small-window] '{}'", crossroadsLeaderboard.toJSONString(JsonViews.DetailedCrossroadsLeaderboard.class));
    }

    @KafkaListener(topics = "${kafka.top.ten.crossroads.topic}${kafka.medium.window}${kafka.topic.suffix}", containerFactory = "kafkaListenerCrossroadsListContainerFactory")
    public void topTenCrossroadsMediumWindowListen(@Payload JNCrossroadsList crossroadsList) {
        LOG.info("[IN][top-10-crossroads-medium-window] '{}'", crossroadsList.toJSONString());

        CrossroadsLeaderboard crossroadsLeaderboard = crossroadsLeaderboardService.save(new CrossroadsLeaderboard(crossroadsList.stream().map(crossroads ->
                new RichCrossroads(crossroadsService.findById(crossroads.getId()).get(), crossroads.getAverageSpeed(), crossroads.getMedianVehiclesCount())
        ).collect(Collectors.toList()), websocketMediumWindow));

        messagingService.sendMessage(crossroadsLeaderboard, JsonViews.DetailedCrossroadsLeaderboard.class, websocketTopTenCrossroadsMediumWindowTopic);
        LOG.info("[OUT][top-10-crossroads-medium-window] '{}'", crossroadsLeaderboard.toJSONString(JsonViews.DetailedCrossroadsLeaderboard.class));
    }

    @KafkaListener(topics = "${kafka.top.ten.crossroads.topic}${kafka.large.window}${kafka.topic.suffix}", containerFactory = "kafkaListenerCrossroadsListContainerFactory")
    public void topTenCrossroadsLargeWindowListen(@Payload JNCrossroadsList crossroadsList) {
        LOG.info("[IN][top-10-crossroads-medium-window] '{}'", crossroadsList.toJSONString());

        CrossroadsLeaderboard crossroadsLeaderboard = crossroadsLeaderboardService.save(new CrossroadsLeaderboard(crossroadsList.stream().map(crossroads ->
                new RichCrossroads(crossroadsService.findById(crossroads.getId()).get(), crossroads.getAverageSpeed(), crossroads.getMedianVehiclesCount())
        ).collect(Collectors.toList()), websocketLargeWindow));

        messagingService.sendMessage(crossroadsLeaderboard, JsonViews.DetailedCrossroadsLeaderboard.class, websocketTopTenCrossroadsLargeWindowTopic);
        LOG.info("[OUT][top-10-crossroads-medium-window] '{}'", crossroadsLeaderboard.toJSONString(JsonViews.DetailedCrossroadsLeaderboard.class));
    }

    @KafkaListener(topics = "${kafka.outlier.crossroads.topic}${kafka.small.window}${kafka.topic.suffix}", containerFactory = "kafkaListenerCrossroadsContainerFactory")
    public void outlierCrossroadsSmallWindowListen(@Payload JNCrossroads crossroads) {
        LOG.info("[IN][outlier-crossroads-small-window] '{}'", crossroads.toJSONString());

        OutlierCrossroads outlierCrossroads = new OutlierCrossroads(crossroadsService.findById(crossroads.getId()).get(), crossroads.getTimestamp(), crossroads.getMedianVehiclesCount(), websocketSmallWindow);

        outlierCrossroadsService.save(outlierCrossroads);

        messagingService.sendMessage(outlierCrossroads, JsonViews.DetailedCrossroads.class, websocketOutliersCrossroadsSmallWindowTopic);
        LOG.info("[OUT][outlier-crossroads-small-window] '{}'", outlierCrossroads.toJSONString(JsonViews.DetailedCrossroads.class));
    }

    @KafkaListener(topics = "${kafka.outlier.crossroads.topic}${kafka.medium.window}${kafka.topic.suffix}", containerFactory = "kafkaListenerCrossroadsContainerFactory")
    public void outlierCrossroadsMediumWindowListen(@Payload JNCrossroads crossroads) {
        LOG.info("[IN][outlier-crossroads-medium-window] '{}'", crossroads.toJSONString());

        OutlierCrossroads outlierCrossroads = new OutlierCrossroads(crossroadsService.findById(crossroads.getId()).get(), crossroads.getTimestamp(), crossroads.getMedianVehiclesCount(), websocketMediumWindow);

        outlierCrossroadsService.save(outlierCrossroads);

        messagingService.sendMessage(outlierCrossroads, JsonViews.DetailedCrossroads.class, websocketOutliersCrossroadsMediumWindowTopic);
        LOG.info("[OUT][outlier-crossroads-medium-window] '{}'", outlierCrossroads.toJSONString(JsonViews.DetailedCrossroads.class));
    }

    @KafkaListener(topics = "${kafka.outlier.crossroads.topic}${kafka.large.window}${kafka.topic.suffix}", containerFactory = "kafkaListenerCrossroadsContainerFactory")
    public void outlierCrossroadsLargeWindowListen(@Payload JNCrossroads crossroads) {
        LOG.info("[IN][outlier-crossroads-large-window] '{}'", crossroads.toJSONString());

        OutlierCrossroads outlierCrossroads = new OutlierCrossroads(crossroadsService.findById(crossroads.getId()).get(), crossroads.getTimestamp(), crossroads.getMedianVehiclesCount(), websocketLargeWindow);

        outlierCrossroadsService.save(outlierCrossroads);

        messagingService.sendMessage(outlierCrossroads, JsonViews.DetailedCrossroads.class, websocketOutliersCrossroadsLargeWindowTopic);
        LOG.info("[OUT][outlier-crossroads-large-window] '{}'", outlierCrossroads.toJSONString(JsonViews.DetailedCrossroads.class));
    }

    @KafkaListener(topics = "${kafka.top.semaphore.route.topic}${kafka.topic.suffix}", containerFactory = "kafkaListenerSemaphoreRouteListContainerFactory")
    public void topSemaphoreRouteListen(@Payload JNSemaphoreRouteList semaphoreRouteList) {
        LOG.info("[IN][top-semaphore-route] '{}'", semaphoreRouteList.toJSONString());

        SemaphoreRouteLeaderboard semaphoreRouteLeaderboard = semaphoreRouteLeaderboardService.save(new SemaphoreRouteLeaderboard(semaphoreRouteList.stream().map(semaphoreRoute ->
                semaphoreRoute.stream().map(semaphorePing ->
                        new RichSemaphore(semaphoreService.findByCrossroads_IdAndSemaphoreId(semaphorePing.getCrossroadsId(), semaphorePing.getSemaphoreId()), semaphorePing.getSpeed())
                ).collect(Collectors.toCollection(SemaphoreRoute::new))
        ).collect(Collectors.toCollection(JSONSerializableList::new)), websocketTopSemaphoreRouteWindow));

        messagingService.sendMessage(semaphoreRouteLeaderboard, JsonViews.DetailedSemaphoreRouteLeaderboard.class, websocketTopSemaphoreRouteTopic);
        LOG.info("[OUT][top-semaphore-route] '{}'", semaphoreRouteLeaderboard.toJSONString(JsonViews.DetailedSemaphoreRouteLeaderboard.class));
    }

    @KafkaListener(topics = "${kafka.damaged.semaphore.topic}${kafka.topic.suffix}", containerFactory = "kafkaListenerDamagedSemaphoreContainerFactory")
    public void damagedSemaphoreListen(@Payload JNDamagedSemaphore damagedSemaphore) {
        LOG.info("[IN][damaged-semaphore] '{}'", damagedSemaphore.toJSONString());

        Semaphore semaphore = semaphoreService.findByCrossroads_IdAndSemaphoreId(damagedSemaphore.getCrossroadsId(), damagedSemaphore.getId());

        boolean needsUpdate = false;
        for (JNLightBulb lightBulb : damagedSemaphore.getDamagedLightBulbs())
            if (!semaphore.getLightBulbStatus(lightBulb.getColor()).equals(lightBulb.getStatus()))
                needsUpdate = true;
        if (!needsUpdate) return;

        for (JNLightBulb lightBulb : damagedSemaphore.getDamagedLightBulbs())
            semaphore.setLightBulbStatus(lightBulb.getColor(), lightBulb.getStatus());
        semaphore = semaphoreService.save(semaphore);

        System.out.println(websocketDamagedSemaphoreTopic);

        messagingService.sendMessage(semaphore, JsonViews.DetailedSemaphore.class, websocketDamagedSemaphoreTopic);
        LOG.info("[OUT][damaged-semaphore] '{}'", semaphore.toJSONString(JsonViews.DetailedSemaphore.class));
    }
}
