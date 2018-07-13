package com.jasmine.jasmine_rest.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jasmine.jasmine_rest.utils.JSONSerializable;
import com.jasmine.jasmine_rest.utils.RandomUtils;

public class JNBoxContainer implements JSONSerializable {
    private JNCoordinates topLeftCoordinates;
    private JNCoordinates bottomRightCoordinates;

    public JNBoxContainer() {
    }

    public JNBoxContainer(JNCoordinates topLeftCoordinates, JNCoordinates bottomRightCoordinates) {
        this.topLeftCoordinates = topLeftCoordinates;
        this.bottomRightCoordinates = bottomRightCoordinates;
    }

    @JsonIgnore
    public Double getHeight() {
        return bottomRightCoordinates.latitude - topLeftCoordinates.latitude;
    }

    @JsonIgnore
    public Double getWidth() {
        return bottomRightCoordinates.longitude - topLeftCoordinates.longitude;
    }

    @JsonIgnore
    public JNCoordinates getCenterCoordinates() {
        return new JNCoordinates((topLeftCoordinates.latitude + bottomRightCoordinates.latitude) / 2, (topLeftCoordinates.longitude + bottomRightCoordinates.longitude) / 2);
    }

    public JNCoordinates getTopLeftCoordinates() {
        return topLeftCoordinates;
    }

    public void setTopLeftCoordinates(JNCoordinates topLeftCoordinates) {
        this.topLeftCoordinates = topLeftCoordinates;
    }

    public JNCoordinates getBottomRightCoordinates() {
        return bottomRightCoordinates;
    }

    public void setBottomRightCoordinates(JNCoordinates bottomRightCoordinates) {
        this.bottomRightCoordinates = bottomRightCoordinates;
    }

    public JNCoordinates randomCoordinates() {
        return new JNCoordinates(RandomUtils.nextDouble(topLeftCoordinates.latitude, bottomRightCoordinates.latitude), RandomUtils.nextDouble(topLeftCoordinates.longitude, bottomRightCoordinates.longitude));
    }
}
