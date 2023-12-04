package io.github.quarkustreinamento.quarkussocial.config;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ForbiddenExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException excecao) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
