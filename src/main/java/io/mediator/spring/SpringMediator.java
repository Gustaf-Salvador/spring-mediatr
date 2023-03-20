package io.mediator.spring;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.mediator.core.Command;
import io.mediator.core.CommandHandler;
import io.mediator.core.Event;
import io.mediator.core.Mediator;
import io.mediator.core.MediatorThreadFactory;
import io.mediator.core.Registry;
import io.mediator.core.Request;
import io.mediator.core.RequestHandler;

public class SpringMediator implements Mediator {

    private static final Logger logger = LoggerFactory.getLogger(SpringMediator.class);
    private final Registry registry;
    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new MediatorThreadFactory());

    public SpringMediator(Registry registry) {
        this.registry = registry;
    }

    public SpringMediator(Registry registry, Executor executor) {
        this.registry = registry;
        this.executor = executor;
        logger.info(executor.getClass().getSimpleName() + " will be used for asynchronous operations instead of the default Executor");
    }

    @Override
    public <TRequest extends Request<TResponse>, TResponse> TResponse dispatch(@Valid TRequest request) {
        final var handler = (RequestHandler) registry.getRequest(request.getClass());
        logger.debug("Dispatching " + request.getClass().getSimpleName() + " to handler " + handler.getClass().getSimpleName());
        return (TResponse) handler.handle(request);
    }

    @Override
    public <TRequest extends Request<TResponse>, TResponse> CompletableFuture<TResponse> dispatchAsync(@Valid TRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            final var handler = registry.getRequest(request.getClass());
            logger.debug("Dispatching " + request.getClass().getSimpleName() + " to handler " + handler.getClass().getSimpleName());
            return (TResponse) handler.handle(request);
        }, executor);
    }

    @Override
    public <tEvent extends Event> void emit(tEvent event) {
        final var eventHandlers = registry.getEvent(event.getClass());

        eventHandlers.forEach(eventHandler -> {
            logger.debug("Dispatching " + event.getClass().getSimpleName() + " to handler " + eventHandler.getClass().getSimpleName());
            eventHandler.handle(event);
        });
    }

    @Override
    public CompletableFuture<Void> emitAsync(@Valid Event event) {
        return CompletableFuture.runAsync(() -> {
            final var eventHandlers = registry.getEvent(event.getClass());

            eventHandlers.forEach(eventHandler -> {
                logger.debug("Dispatching " + event.getClass().getSimpleName() + " to handler " + eventHandler.getClass().getSimpleName());
                eventHandler.handle(event);
            });

        }, executor);
    }

    @Override
    public void dispatch(@Valid Command command) {
        final var handler = (CommandHandler) registry.getCommand(command.getClass());
        logger.debug("Dispatching " + command.getClass().getSimpleName() + " to handler " + handler.getClass().getSimpleName());
        handler.handle(command);
    }

    @Override
    public CompletableFuture<Void> dispatchAsync(@Valid Command command) {
        return CompletableFuture.runAsync(() -> {
            final var handler = (CommandHandler) registry.getCommand(command.getClass());
            logger.debug("Dispatching " + command.getClass().getSimpleName() + " to handler " + handler.getClass().getSimpleName());
            handler.handle(command);
        }, executor);
    }
}
