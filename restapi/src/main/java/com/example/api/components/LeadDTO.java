package com.example.api.components;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LeadDTO {

    @Getter @Setter
    String id;
    @Getter @Setter
    String name;

    @Getter @Setter
    List<Campaign> sourceCampaigns;

    public LeadDTO(String id, String name, List<Campaign> sourceCampaigns) {
        this.id = id;
        this.name = name;
        this.sourceCampaigns = sourceCampaigns;
    }

    public LeadDTO() {
    }
}
