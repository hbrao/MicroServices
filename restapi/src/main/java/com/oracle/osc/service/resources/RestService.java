package com.oracle.osc.service.resources;

import java.io.StringReader;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Map;

import com.oracle.osc.service.util.Utils;
import javax.ws.rs.core.Response.Status;

@Path("")
@RequestScoped
public class RestService {

    public JsonObject parseAndCleanJsonString(String jsonString) {
        // Clean the string if it's overly quoted
        String cleanedJsonString = jsonString.trim();
        if (cleanedJsonString.startsWith("\"") && cleanedJsonString.endsWith("\"")) {
            cleanedJsonString = cleanedJsonString.substring(1, cleanedJsonString.length() - 1);
        }
        cleanedJsonString = cleanedJsonString.replace("\\\"", "\"");

        // Parse the cleaned string into a JsonObject
        try (JsonReader jsonReader = Json.createReader(new StringReader(cleanedJsonString))) {
            JsonObject jsonObject = jsonReader.readObject();
            return jsonObject;
        } // try-with-resources ensures jsonReader is closed automatically
    }

    @POST
    @Path("/leads")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLead(String requestBody) {
        JsonObject inputObject = parseAndCleanJsonString(requestBody);
        JsonObject newLead = Json.createObjectBuilder()
                .add("name", inputObject.get("Name"))
                .add("id", UUID.randomUUID().toString())
                .build();
        return Response.status(Status.CREATED)
                       .entity(newLead)
                       .build();
    }

    @GET
    @Path("/leads/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeadById(@PathParam("id") String id) {
        return Response.status(Status.OK).entity(Utils.convertToJsonObject(Map.of("Lead " + id, Long.valueOf(id)))).build();
    }


    @GET
    @Path("/{object}/{objectId}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response history(@PathParam("object") final String objectName,
                            @PathParam("objectId") final String objectId,
                            @Context UriInfo uriInfo,
                            @Context HttpHeaders httpHeaders ) {

        System.out.println("Fetching " + objectName + " history .... ");
        JsonObject responseObject = Utils.convertToJsonObject(Map.of("id",objectId));
        return Response.status(Response.Status.OK).entity(responseObject).build();
    }
}
