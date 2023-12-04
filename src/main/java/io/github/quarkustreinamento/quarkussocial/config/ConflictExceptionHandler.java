package io.github.quarkustreinamento.quarkussocial.config;

import io.github.quarkustreinamento.quarkussocial.exceptions.ConflictException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ConflictExceptionHandler implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException excecao) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErroResponse(excecao.getMessage(), 400))
                .build();
    }
}
