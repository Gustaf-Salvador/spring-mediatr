package io.jkratz.mediator.core;


public interface RequestHandler <TRequest extends Request<TResponse>, TResponse> {

    public TResponse handle(TRequest request);

}
