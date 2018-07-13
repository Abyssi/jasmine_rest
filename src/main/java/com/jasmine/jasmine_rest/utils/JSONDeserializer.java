package com.jasmine.jasmine_rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONDeserializer {

    public static <T> T FromJSONStringE(String jsonString, Class<T> typeClass) throws IOException {
        return new ObjectMapper().readValue(jsonString, typeClass);
    }

    public static <T> T FromJSONString(String jsonString, Class<T> typeClass) {
        try {
            return JSONDeserializer.FromJSONStringE(jsonString, typeClass);
        } catch (Exception e) {
            return null;
        }
    }
}
