package com.jasmine.jasmine_rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface JSONSerializable {

    default <T> String toJSONStringE(Class<T> tClass) throws JsonProcessingException {
        return (tClass == null ? new ObjectMapper().writer() : new ObjectMapper().writerWithView(tClass)).writeValueAsString(this);
    }

    default <T> String toJSONString(Class<T> tClass) {
        try {
            return this.toJSONStringE(tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Cannot serialize object";
        }
    }

    default <T> String toString(Class<T> tClass) {
        return this.toJSONString(tClass);
    }

    default String toJSONStringE() throws JsonProcessingException {
        return toJSONStringE(null);
    }

    default String toJSONString() {
        return toJSONString(null);
    }
}
