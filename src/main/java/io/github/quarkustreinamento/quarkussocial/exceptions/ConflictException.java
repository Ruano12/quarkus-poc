package io.github.quarkustreinamento.quarkussocial.exceptions;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

public class ConflictException extends BadRequestException {

    public ConflictException(String message) {
        super(message);
    }

}
