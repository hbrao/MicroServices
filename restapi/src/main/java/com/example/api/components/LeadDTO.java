package com.example.api.components;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LeadDTO {

    @Getter @Setter
    String id;
    @Getter @Setter
    String name;
}
