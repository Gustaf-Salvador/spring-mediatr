package io.mediator.core;

import java.util.Set;

import io.mediator.core.exception.NoCommandHandlerException;
import io.mediator.core.exception.NoEventHandlersException;
import io.mediator.core.exception.NoRequestHandlerException;

public interface Registry {

    <C extends Request<R>, R> RequestHandler<C, R> getRequest(Class<? extends C> requestClass) throws NoRequestHandlerException;

    <E extends Event> Set<EventHandler<E>> getEvent(Class<? extends E> eventClass) throws NoEventHandlersException;

    <C extends Command> CommandHandler<C> getCommand(Class<? extends C> commandClass) throws NoCommandHandlerException;
}
