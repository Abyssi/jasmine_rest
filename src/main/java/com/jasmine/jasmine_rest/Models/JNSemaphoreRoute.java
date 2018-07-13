package com.jasmine.jasmine_rest.Models;

import com.jasmine.jasmine_rest.utils.JSONSerializableList;

public class JNSemaphoreRoute extends JSONSerializableList<JNSemaphorePing> {
    private String id;
    private double averageSpeed;

    public JNSemaphoreRoute() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
