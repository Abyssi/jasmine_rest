package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.utils.JSONSerializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

@Document
public class CrossroadsLeaderboard implements JSONSerializable {
    @Id
    @JsonIgnore
    private String id;

    @JsonView(JsonViews.DetailedCrossroadsLeaderboard.class)
    @NonNull
    private List<RichCrossroads> list;

    @JsonView(JsonViews.Basic.class)
    @NonNull
    private Integer timeWindowMilliseconds;

    public List<RichCrossroads> getList() {
        return this.list == null ? (this.list = new ArrayList<>()) : this.list;
    }
}
