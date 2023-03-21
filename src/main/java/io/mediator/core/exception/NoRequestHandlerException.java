package io.mediator.core.exception;

public class NoRequestHandlerException extends RuntimeException {

    private static final long serialVersionUID = 5634938850754503925L;

	public NoRequestHandlerException(String Message) {
        super (Message);
    }
}
