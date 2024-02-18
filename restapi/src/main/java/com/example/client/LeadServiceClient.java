package com.example.client;

import javax.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Path("/crm")
@ClientHeaderParam(name = "Authorization", value = "{getAuthorization}")
public interface LeadServiceClient {

    @POST
    @Path("/leads")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createLead(JsonObject leadRequest);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/leads/{id}")
    JsonObject getLeadById(@PathParam("id") String id);

    default String getAuthorization() {
        return "Basic " + Base64.getEncoder().encodeToString(("username:password").getBytes(StandardCharsets.UTF_8));
    }
}
