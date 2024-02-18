package com.example.api.service;

import com.example.api.components.Campaign;
import com.example.api.components.LeadDTO;
import java.util.UUID;
import org.jboss.weld.util.collections.ImmutableList;

public class LeadService {

    public LeadDTO getLeadById(String id) {
        return  LeadDTO.builder()
                       .id(id)
                       .name("Existing Lead 01")
                       .sourceCampaigns(ImmutableList.of(Campaign.builder()
                               .name("Campaign 01").location("NA").build()
                           )
                       )
                       .build();
    }

    public LeadDTO createLead(LeadDTO lead) {
        return  LeadDTO.builder()
                .id(UUID.randomUUID().toString())
                .name(lead.getName())
                .sourceCampaigns(lead.getSourceCampaigns())
                .build();
    }
}
