package com.example.api.controller;

import com.example.api.service.LeadService;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class ControllerFactory {

    public static LeadController getLeadController() {
       return new LeadController(new LeadService());
    }
}
