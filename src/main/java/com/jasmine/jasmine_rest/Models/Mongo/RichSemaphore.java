package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)

@Document
public class RichSemaphore extends DBDecorable<Semaphore> {
    @JsonView({JsonViews.DetailedSemaphore.class, JsonViews.DetailedSemaphoreRouteLeaderboard.class})
    private double speed;

    public RichSemaphore() {
        super(null);
    }

    public RichSemaphore(Semaphore base, double speed) {
        super(base);
        this.speed = speed;
    }
}
