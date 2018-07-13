package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.service.cascade.CascadeSave;
import com.jasmine.jasmine_rest.utils.JSONSerializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor

@Document
public class Crossroads implements JSONSerializable {
    @Id
    @JsonView(JsonViews.IdentifierOnly.class)
    private String id;

    @DBRef
    @CascadeSave
    @JsonView({JsonViews.DetailedCrossroads.class, JsonViews.DetailedCrossroadsLeaderboard.class})
    private List<Semaphore> semaphores;

    public List<Semaphore> getSemaphores() {
        return this.semaphores == null ? (this.semaphores = new ArrayList<>()) : this.semaphores;
    }
}
