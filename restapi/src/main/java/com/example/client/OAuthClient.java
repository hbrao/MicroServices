package com.example.client;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/oauth2/v1")
public interface OAuthClient {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/token")
    JsonObject getToken(
            @NotNull @HeaderParam("Authorization") String authorization,
            @NotNull String body
    );
}