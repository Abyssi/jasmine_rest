package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.utils.JSONSerializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessagingService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public void sendMessage(String message, String topic) {
        this.simpMessagingTemplate.convertAndSend(topic, message);
    }

    @Transactional
    public <T extends JSONSerializable> void sendMessage(T t, String topic) {
        this.sendMessage(t.toJSONString(), topic);
    }

    @Transactional
    public <T extends JSONSerializable, V> void sendMessage(T t, Class<V> vClass, String topic) {
        this.sendMessage(t.toJSONString(vClass), topic);
    }
}
