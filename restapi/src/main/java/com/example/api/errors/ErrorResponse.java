package com.example.api.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorResponse {
    private String error;
    private String location;
    private String field;
    private String code;
    private String developerMessage;
    private String moreInfo;
    private Map<Integer,String> details;

    public ErrorResponse(String code, String error) {
        this.code = code;
        this.error = error;
    }
}
