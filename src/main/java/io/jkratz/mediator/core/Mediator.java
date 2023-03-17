package io.jkratz.mediator.core;

import java.util.concurrent.CompletableFuture;

/**
 * Defines a mediator to encapsulate dispatching and publishing interaction patterns.
 *
 * @author Joseph Kratz
 * @since 1.0
 */
public interface Mediator {

    /**
     * Dispatches a {@link Request} to a single {@link RequestHandler} synchronously.
     *
     * @param <TRequest>  the type of the request
     * @param <TResponse> the type of the response
     * @param request     the request to be executed
     * @return the response
     */
    <TRequest extends Request<TResponse>, TResponse> TResponse dispatch(TRequest request);

    /**
     * Dispatches a {@link Request} to a single {@link RequestHandler} to execute asynchronously on another thread.
     *
     * @param <TRequest>  the type of the request
     * @param <TResponse> the type of the response
     * @param request     the request to be executed
     * @return a future that completes with the response
     */
    <TRequest extends Request<TResponse>, TResponse> CompletableFuture<TResponse> dispatchAsync(TRequest request);

    /**
     * Sends the event to all registered {@link EventHandler}s for the particular event synchronously.
     *
     * @param event the event to send
     */
    void emit(Event event);

    /**
     * Sends the event to all registered {@link EventHandler}s for the particular event asynchronously on another thread.
     *
     * @param event the event to send
     * @return a future that completes when all handlers have handled the event
     */
    CompletableFuture<Void> emitAsync(Event event);

    /**
     * Dispatches a {@link Command} to a single {@link CommandHandler} synchronously.
     *
     * @param command the command to dispatch for execution
     */
    void dispatch(Command command);

    /**
     * Dispatches a {@link Command} to a single {@link CommandHandler} to execute asynchronously on another thread.
     *
     * @param command the command to dispatch for execution
     * @return a future that completes when the command has been executed
     */
    CompletableFuture<Void> dispatchAsync(Command command);
}
