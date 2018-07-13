package com.jasmine.jasmine_rest.Models.Mongo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.io.IOException;

public class GeoJsonPointJsonDeserializer extends JsonDeserializer<GeoJsonPoint> {

    @Override
    public GeoJsonPoint deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode geoJsonNode = jsonParser.getCodec().readTree(jsonParser);
        return new GeoJsonPoint(
                geoJsonNode.get("longitude").asDouble(),
                geoJsonNode.get("latitude").asDouble()
        );
    }
}
