package io.mediator.core;

import java.util.concurrent.CompletableFuture;


public interface Mediator {


    <TRequest extends Request<TResponse>, TResponse> TResponse dispatch(TRequest request);

    <TRequest extends Request<TResponse>, TResponse> CompletableFuture<TResponse> dispatchAsync(TRequest request);

    <tEvent extends Event> void emit(tEvent event);

    CompletableFuture<Void> emitAsync(Event event);

    void dispatch(Command command);

    CompletableFuture<Void> dispatchAsync(Command command);
}
