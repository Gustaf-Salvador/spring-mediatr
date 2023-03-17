package io.jkratz.mediator.core;

import io.jkratz.mediator.core.exception.NoCommandHandlerException;
import io.jkratz.mediator.core.exception.NoEventHandlersException;
import io.jkratz.mediator.core.exception.NoRequestHandlerException;

import java.util.Set;

public interface Registry {

    <C extends Request<R>, R> RequestHandler<C, R> getRequest(Class<? extends C> requestClass) throws NoRequestHandlerException;

    <E extends Event> Set<EventHandler<E>> getEvent(Class<? extends E> eventClass) throws NoEventHandlersException;

    <C extends Command> CommandHandler<C> getCommand(Class<? extends C> commandClass) throws NoCommandHandlerException;
}