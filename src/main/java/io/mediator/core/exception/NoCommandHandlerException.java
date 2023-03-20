package io.mediator.core.exception;

public class NoCommandHandlerException extends RuntimeException {

    public NoCommandHandlerException(String Message) {
        super (Message);
    }
}
