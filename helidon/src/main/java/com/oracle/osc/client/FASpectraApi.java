package com.oracle.osc.client;

import com.oracle.osc.service.util.Utils;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/feeds")
@ClientHeaderParam(name = "Authorization", value = "{getSpectraAuthorization}")
public interface FASpectraApi {

    @GET
    @Path("/{object}/{objectId}/history")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getHistory(@PathParam("object") String object, @PathParam("objectId") String objectId);

    default  String getSpectraAuthorization() {
        return "Bearer " + Utils.getOAuthToken();
    }
}
