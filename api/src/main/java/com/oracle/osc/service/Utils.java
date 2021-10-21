package com.oracle.osc.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;

public class Utils {
    public static JsonObject convertToJsonObject(Map<String, Object> queryResultMap) {
        JsonObject response = null;
        if (queryResultMap != null) {
            JsonObjectBuilder responseBuilder = null;
            if (queryResultMap.size() > 0) {
                responseBuilder = Json.createObjectBuilder();
                for(Map.Entry<String, Object> entry : queryResultMap.entrySet()) {
                    responseBuilder.add(entry.getKey(), entry.getValue().toString());
                }

                response = responseBuilder != null ? responseBuilder.build() : null;
            }
        }

        return response;
    }
}
