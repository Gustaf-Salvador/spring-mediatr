package io.mediator.core.exception;

public class DuplicateRequestHandlerRegistrationException extends RuntimeException {

    private static final long serialVersionUID = 4153965977656655971L;

	public DuplicateRequestHandlerRegistrationException(String Message) {
        super (Message);
    }
}
