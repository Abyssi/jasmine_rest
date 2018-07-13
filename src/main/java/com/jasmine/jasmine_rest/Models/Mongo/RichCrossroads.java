package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)

@Document
public class RichCrossroads extends DBDecorable<Crossroads> {
    @JsonView({JsonViews.DetailedCrossroads.class, JsonViews.DetailedCrossroadsLeaderboard.class})
    private double averageSpeed;

    @JsonView({JsonViews.DetailedCrossroads.class, JsonViews.DetailedCrossroadsLeaderboard.class})
    private double medianVehiclesCount;

    public RichCrossroads() {
        super(null);
    }

    public RichCrossroads(Crossroads base, double averageSpeed, double medianVehiclesCount) {
        super(base);
        this.averageSpeed = averageSpeed;
        this.medianVehiclesCount = medianVehiclesCount;
    }
}
