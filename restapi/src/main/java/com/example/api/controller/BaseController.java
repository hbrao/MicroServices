package com.example.api.controller;

import com.example.api.service.LeadService;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class BaseController {

    public static BaseController getInstance(String implType) throws NotImplementedException {
        switch (implType) {
            case "lead":
                return new LeadController(new LeadService());
            default:
                throw new NotImplementedException("Invalid impl type");
        }
    }
}
