package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)

@Document
public class OutlierCrossroads extends DBDecorable<Crossroads> {
    @JsonView(JsonViews.DetailedCrossroads.class)
    private long timestamp;

    @JsonView(JsonViews.DetailedCrossroads.class)
    private double medianVehiclesCount;

    @JsonView(JsonViews.Basic.class)
    @NonNull
    private Integer timeWindowMilliseconds;

    public OutlierCrossroads() {
        super(null);
    }

    public OutlierCrossroads(Crossroads base, long timestamp, double medianVehiclesCount, Integer timeWindowMilliseconds) {
        super(base);
        this.timestamp = timestamp;
        this.medianVehiclesCount = medianVehiclesCount;
        this.timeWindowMilliseconds = timeWindowMilliseconds;
    }
}
