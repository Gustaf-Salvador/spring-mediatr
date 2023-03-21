package io.mediator.core.exception;

public class NoCommandHandlerException extends RuntimeException {

    private static final long serialVersionUID = 3787105656069128315L;

	public NoCommandHandlerException(String Message) {
        super (Message);
    }
}
