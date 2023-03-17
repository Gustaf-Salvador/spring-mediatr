package io.jkratz.mediator.core.exception;

public class DuplicateCommandHandlerRegistrationException extends RuntimeException {

    public DuplicateCommandHandlerRegistrationException(String Message) {
        super (Message);
    }
}
