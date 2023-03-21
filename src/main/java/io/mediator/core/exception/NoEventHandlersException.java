package io.mediator.core.exception;

public class NoEventHandlersException extends RuntimeException {

    private static final long serialVersionUID = 2448266039604960953L;

	public NoEventHandlersException(String Message) {
        super (Message);
    }
}
