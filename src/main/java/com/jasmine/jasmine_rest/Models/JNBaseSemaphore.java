package com.jasmine.jasmine_rest.Models;

import com.jasmine.jasmine_rest.utils.JSONSerializable;

public class JNBaseSemaphore implements JSONSerializable {

    private String crossroadsId;
    private String semaphoreId;
    private JNCoordinates position;

    public JNBaseSemaphore() {
    }

    public JNBaseSemaphore(String crossroadsId, String semaphoreId, JNCoordinates position) {
        this.crossroadsId = crossroadsId;
        this.semaphoreId = semaphoreId;
        this.position = position;
    }

    /*
        Getter and Setter
     */

    public String getCrossroadsId() {
        return this.crossroadsId;
    }

    public void setCrossroadsId(String crossroadsId) {
        this.crossroadsId = crossroadsId;
    }

    public String getSemaphoreId() {
        return this.semaphoreId;
    }

    public void setSemaphoreId(String semaphoreId) {
        this.semaphoreId = semaphoreId;
    }

    public JNCoordinates getPosition() {
        return this.position;
    }

    public void setPosition(JNCoordinates position) {
        this.position = position;
    }
}
