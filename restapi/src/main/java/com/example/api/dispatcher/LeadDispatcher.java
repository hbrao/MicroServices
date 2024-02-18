package com.example.api.dispatcher;

import com.example.api.components.LeadDTO;
import com.example.api.controller.ControllerFactory;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/crm")
@RequestScoped
public class LeadDispatcher {

    @GET
    @Path("/leads/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary="Get Lead by ID")
    @APIResponse(description = "Successfully Retrieved", responseCode = "200")
    public Response getLeadById(@PathParam("id") String id) throws NotImplementedException {
        return ControllerFactory.getLeadController().getLeads(id);
    }

    @POST
    @Path(("/leads"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new lead")
    @APIResponse(description = "Successfully created new lead", responseCode = "201")
    public Response createLead(LeadDTO leadDTO) throws NotImplementedException {
        return ControllerFactory.getLeadController().createLead(leadDTO);
    }
}
