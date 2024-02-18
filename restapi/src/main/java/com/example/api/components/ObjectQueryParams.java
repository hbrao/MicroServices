package com.example.api.components;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.QueryParam;

@RequestScoped
public class ObjectQueryParams {
    @QueryParam("fields")
    private String fields;

    public ObjectQueryParams() {
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
