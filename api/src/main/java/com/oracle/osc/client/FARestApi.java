package com.oracle.osc.client;

import javax.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Path("")
@ClientHeaderParam(name = "Authorization", value = "{getAuthorization}")
public interface FARestApi {

    @POST
    @Path("/leads")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createLead(JsonObject leadRequest);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/leads/{id}")
    JsonObject getLeadById(@PathParam("id") String id);

    default String getAuthorization() {
        return "Basic " + Base64.getEncoder().encodeToString(("sranard:Welcome1").getBytes(StandardCharsets.UTF_8));
    }
}
