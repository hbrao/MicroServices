package com.example.api.controller;

import com.example.api.components.LeadDTO;
import com.example.api.service.LeadService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class LeadController {

    LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    public Response getLeadById(String leadId) {
        LeadDTO lead = leadService.getLeadById(leadId);
        return Response.ok().entity(lead).build();
    }

    public Response createLead(LeadDTO leadDTO) {
        return Response.status(Status.CREATED).entity(leadService.createLead(leadDTO)).build();
    }
}
