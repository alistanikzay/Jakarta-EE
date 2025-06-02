package org.example.bookapi.Exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class  ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse())
                .build();
    }
}