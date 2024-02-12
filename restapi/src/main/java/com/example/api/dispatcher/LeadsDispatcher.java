package com.example.api.dispatcher;

import com.example.api.controller.BaseController;
import com.example.api.controller.LeadController;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

@Path("")
@RequestScoped
public class LeadsDispatcher {

    @GET
    @Path("/leads/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeadById(@PathParam("id") String id) {
        try {
            LeadController leadController = (LeadController) BaseController.getInstance("lead");
            return leadController.getLeads(id);
        } catch (NotImplementedException e) {
            return Response.serverError().build();
        }
    }
}
