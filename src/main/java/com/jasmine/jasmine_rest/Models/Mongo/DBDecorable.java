package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.jasmine.jasmine_rest.utils.JSONSerializable;
import org.springframework.data.mongodb.core.mapping.DBRef;

public abstract class DBDecorable<T> implements JSONSerializable {
    @DBRef
    @JsonUnwrapped
    private T base;

    public DBDecorable(T base) {
        this.base = base;
    }

    public T getBase() {
        return base;
    }

    public void setBase(T base) {
        this.base = base;
    }
}
