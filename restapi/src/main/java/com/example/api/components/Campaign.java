package com.example.api.components;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Campaign {
    @Getter @Setter
    String name;
    @Getter @Setter
    String location;

    public Campaign(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Campaign() {
    }
}
