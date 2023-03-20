package io.mediator.core.exception;

public class NoEventHandlersException extends RuntimeException {

    public NoEventHandlersException(String Message) {
        super (Message);
    }
}
