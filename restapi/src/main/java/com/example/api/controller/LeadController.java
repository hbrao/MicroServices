package com.example.api.controller;

import com.example.api.components.LeadDTO;
import com.example.api.service.LeadService;
import javax.ws.rs.core.Response;

public class LeadController extends  BaseController {

    LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    public Response getLeads(String leadId) {
        LeadDTO lead = leadService.getLeadById(leadId);
        return Response.ok().entity(lead).build();
    }

}
