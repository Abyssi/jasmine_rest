package com.jasmine.jasmine_rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

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
    private String websocketOutliersCrossroadsTopic;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(websocketTopTenCrossroadsSmallWindowTopic,
                websocketTopTenCrossroadsMediumWindowTopic,
                websocketTopTenCrossroadsLargeWindowTopic,
                websocketOutliersCrossroadsSmallWindowTopic,
                websocketOutliersCrossroadsMediumWindowTopic,
                websocketOutliersCrossroadsLargeWindowTopic,
                websocketDamagedSemaphoreTopic,
                websocketOutliersCrossroadsTopic);
    }
}
