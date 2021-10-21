package com.oracle.osc.service.oauth;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/oauth2/v1")
public interface FAIdcsRestApi {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/token")
    JsonObject getToken(
            /* @NotNull @HeaderParam("X-USER-IDENTITY-DOMAIN-NAME") String identityDomainName, */
            @NotNull @HeaderParam("Authorization") String authorization,
            @NotNull @RequestBody String body
    );
}
