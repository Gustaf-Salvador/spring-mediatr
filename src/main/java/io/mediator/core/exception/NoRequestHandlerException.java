package io.mediator.core.exception;

public class NoRequestHandlerException extends RuntimeException {

    public NoRequestHandlerException(String Message) {
        super (Message);
    }
}
