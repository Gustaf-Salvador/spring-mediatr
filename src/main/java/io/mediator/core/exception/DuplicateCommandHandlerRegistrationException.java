package io.mediator.core.exception;

public class DuplicateCommandHandlerRegistrationException extends RuntimeException {

	private static final long serialVersionUID = -4599409645538642425L;

	public DuplicateCommandHandlerRegistrationException(String Message) {
        super (Message);
    }
}
