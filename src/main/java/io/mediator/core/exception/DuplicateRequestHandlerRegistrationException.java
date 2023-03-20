package io.mediator.core.exception;

public class DuplicateRequestHandlerRegistrationException extends RuntimeException {

    public DuplicateRequestHandlerRegistrationException(String Message) {
        super (Message);
    }
}
