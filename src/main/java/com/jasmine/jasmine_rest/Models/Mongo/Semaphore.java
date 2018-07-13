package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jasmine.jasmine_rest.Models.JNLightBulb;
import com.jasmine.jasmine_rest.Models.JNLightBulbColor;
import com.jasmine.jasmine_rest.Models.JNLightBulbStatus;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.utils.JSONSerializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

@Document
public class Semaphore implements JSONSerializable {
    @Id
    private String id;

    @NonNull
    @JsonView(JsonViews.Basic.class)
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @JsonDeserialize(using = GeoJsonPointJsonDeserializer.class)
    @JsonSerialize(using = GeoJsonPointJsonSerializer.class)
    private GeoJsonPoint location;

    @JsonView({JsonViews.DetailedSemaphore.class, JsonViews.DetailedSemaphoreRouteLeaderboard.class})
    private int greenTime;

    @JsonView(JsonViews.Basic.class)
    private List<JNLightBulb> lightBulbs;

    @NonNull
    @DBRef
    @JsonView({JsonViews.DetailedSemaphore.class, JsonViews.DetailedSemaphoreRouteLeaderboard.class})
    private Crossroads crossroads;

    @NonNull
    @JsonView(JsonViews.Basic.class)
    private String semaphoreId;

    public List<JNLightBulb> getLightBulbs() {
        return this.lightBulbs == null ? (this.lightBulbs = new ArrayList<>()) : this.lightBulbs;
    }

    public JNLightBulbStatus getLightBulbStatus(JNLightBulbColor color) {
        for (JNLightBulb lightBulb : getLightBulbs())
            if (lightBulb.getColor().equals(color))
                return lightBulb.getStatus();
        return null;
    }

    public void setLightBulbStatus(JNLightBulbColor color, JNLightBulbStatus status) {
        for (JNLightBulb lightBulb : getLightBulbs())
            if (lightBulb.getColor().equals(color))
                lightBulb.setStatus(status);
    }

    public void setLightBulbsStatus(JNLightBulbStatus status) {
        for (JNLightBulb lightBulb : getLightBulbs())
            lightBulb.setStatus(status);
    }
}
