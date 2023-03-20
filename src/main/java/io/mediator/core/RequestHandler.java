package io.mediator.core;


public interface RequestHandler <TRequest extends Request<TResponse>, TResponse> {

    public TResponse handle(TRequest request);

}
