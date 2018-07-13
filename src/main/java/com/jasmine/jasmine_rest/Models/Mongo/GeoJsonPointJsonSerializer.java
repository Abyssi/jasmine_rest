package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.io.IOException;

public class GeoJsonPointJsonSerializer extends JsonSerializer<GeoJsonPoint> {
    @Override
    public void serialize(GeoJsonPoint geoJsonPoint, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("latitude", String.valueOf(geoJsonPoint.getY()));
        jsonGenerator.writeStringField("longitude", String.valueOf(geoJsonPoint.getX()));
        jsonGenerator.writeEndObject();
    }
}
