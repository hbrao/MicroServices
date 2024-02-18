package com.example.client.impl;

import com.example.client.LeadServiceClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class LeadServiceClientImpl implements LeadServiceClient {

    private LeadServiceClient restClientServiceClient;

    public LeadServiceClient getRestApiClient() {
        if( restClientServiceClient == null ) createRestApiClient();
        return restClientServiceClient;
    }

    private synchronized  void createRestApiClient() {
        if( restClientServiceClient == null ) {
            restClientServiceClient = RestClientBuilder.newBuilder()
                                         .baseUri(URI.create("http://localhost:8081/api"))
                                         .connectTimeout(5, TimeUnit.SECONDS)
                                         .readTimeout(1, TimeUnit.MINUTES)
                                         .build(LeadServiceClient.class);
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
}
