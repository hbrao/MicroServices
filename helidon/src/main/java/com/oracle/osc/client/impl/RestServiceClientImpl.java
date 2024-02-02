package com.oracle.osc.client.impl;

import com.oracle.osc.client.RestServiceClient;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class RestServiceClientImpl implements RestServiceClient {

    private RestServiceClient restClientServiceClient;

    public RestServiceClient getRestApiClient() {
        if( restClientServiceClient == null ) createRestApiClient();
        return restClientServiceClient;
    }

    private synchronized  void createRestApiClient() {
        if( restClientServiceClient == null ) {
            restClientServiceClient = RestClientBuilder.newBuilder()
                                         .baseUri(URI.create("http://localhost:8081"))
                                         .connectTimeout(5, TimeUnit.SECONDS)
                                         .readTimeout(1, TimeUnit.MINUTES)
                                         .build(RestServiceClient.class);
        }
    }

    @Override
    public JsonObject getLeadById(String id) {
        return getRestApiClient().getLeadById(id);
    }

    @Override
    public Response createLead(JsonObject leadRequest) {
        return getRestApiClient().createLead(leadRequest);
    }

    @Override
    public JsonObject getHistory(String object, String objectId) {
        return getRestApiClient().getHistory(object, objectId);
    }
}
