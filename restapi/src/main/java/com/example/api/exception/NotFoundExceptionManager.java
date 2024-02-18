package com.example.api.exception;

import com.example.api.errors.ErrorResponse;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionManager implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Status.NOT_FOUND).entity(new ErrorResponse("OBJ_NOT_FOUND", e.getMessage())).build();
    }
}
