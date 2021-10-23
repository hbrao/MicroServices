package com.oracle.osc.service.resources;

import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Map;

import com.oracle.osc.service.util.Utils;

@Path("/crmRestApi/resources/11.13.18.05/feeds")
@RequestScoped
public class SampleResource {
    @GET
    @Path("/{object}/{objectId}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response history(@PathParam("object") final String objectName,
                            @PathParam("objectId") final String objectId,
                            @Context UriInfo uriInfo,
                            @Context HttpHeaders httpHeaders ) {

        System.out.println("Fetching " + objectName + " history .... ");
        JsonObject responseObject = Utils.convertToJsonObject(Map.of(objectName, Long.valueOf(objectId)));
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }
}
