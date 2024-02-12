package com.example.api.service;

import com.example.api.components.LeadDTO;
import java.util.UUID;

public class LeadService {

    public LeadDTO getLeadById(String id) {
        return  LeadDTO.builder()
                       .id(UUID.randomUUID().toString())
                       .name("Existing Lead 01")
                       .build();
    }
}
