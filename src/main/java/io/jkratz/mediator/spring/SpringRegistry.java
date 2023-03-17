package io.jkratz.mediator.spring;

import java.util.HashSet;
import java.util.Set;
import io.jkratz.mediator.core.Command;
import io.jkratz.mediator.core.CommandHandler;
import io.jkratz.mediator.core.Event;
import io.jkratz.mediator.core.EventHandler;
import io.jkratz.mediator.core.Registry;
import io.jkratz.mediator.core.Request;
import io.jkratz.mediator.core.RequestHandler;
import io.jkratz.mediator.core.exception.DuplicateCommandHandlerRegistrationException;
import io.jkratz.mediator.core.exception.DuplicateRequestHandlerRegistrationException;
import io.jkratz.mediator.core.exception.NoCommandHandlerException;
import io.jkratz.mediator.core.exception.NoEventHandlersException;
import io.jkratz.mediator.core.exception.NoRequestHandlerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.GenericTypeResolver;

public class SpringRegistry implements Registry {

    private final ApplicationContext applicationContext;
    private final Map<Class<? extends Request<?>>, RequestHandlerProvider<?>> requestRegistry = new HashMap<>();
    private final Map<Class<? extends Event>, Set<EventHandlerProvider<?>>> eventRegistry = new HashMap<>();
    private final Map<Class<? extends Command>, CommandHandlerProvider<?>> commandRegistry = new HashMap<>();
    private boolean initialized = false;
    private static final Logger logger = LoggerFactory.getLogger(SpringRegistry.class);

    public SpringRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <C extends Request<R>, R> RequestHandler<C, R> getRequest(Class<? extends C> requestClass) {
        if (!initialized) {
            initializeHandlers();
        }
        RequestHandlerProvider<?> provider = requestRegistry.get(requestClass);
        if (provider != null) {
            @SuppressWarnings("unchecked")
            RequestHandler<C, R> handler = (RequestHandler<C, R>) provider.getHandler();
            return handler;
        } else {
            throw new NoRequestHandlerException("No RequestHandler is registered to handle request of type " + requestClass.getCanonicalName());
        }
    }


    @Override
    public <E extends Event> Set<EventHandler<E>> getEvent(Class<? extends E> eventClass) {
        if (!initialized) {
            initializeHandlers();
        }
        Set<EventHandler<E>> handlers = new HashSet<>();
        Set<EventHandlerProvider<?>> providers = eventRegistry.get(eventClass);
        if (providers != null) {
            for (EventHandlerProvider<?> provider : providers) {
                @SuppressWarnings("unchecked")
                EventHandler<E> handler = (EventHandler<E>) provider.getHandler();
                handlers.add(handler);
            }
        } else {
            throw new NoEventHandlersException("No EventHandlers are registered to handle event of type " + eventClass.getCanonicalName());
        }
        return handlers;
    }

    @Override
    public <C extends Command> CommandHandler<C> getCommand(Class<? extends C> commandClass) {
        if (!initialized) {
            initializeHandlers();
        }
        CommandHandlerProvider<?> provider = commandRegistry.get(commandClass);
        if (provider != null) {
            @SuppressWarnings("unchecked")
            CommandHandler<C> handler = (CommandHandler<C>) provider.getHandler();
            return handler;
        } else {
            throw new NoCommandHandlerException("No CommandHandler is registered to handle request of type " + commandClass.getCanonicalName());
        }
    }

    private void initializeHandlers() {
        synchronized (this) {
            if (!initialized) {
                applicationContext.getBeansOfType(RequestHandler.class)
                    .forEach((name, handler) -> registerRequestHandler(handler.getClass().getName()));
                applicationContext.getBeansOfType(EventHandler.class)
                    .forEach((name, handler) -> registerEventHandler(handler.getClass().getName()));
                applicationContext.getBeansOfType(CommandHandler.class)
                    .forEach((name, handler) -> registerCommandHandler(handler.getClass().getName()));
                initialized = true;
            }
        }
    }

    private void registerRequestHandler(String name) {
        logger.debug("Registering RequestHandler with name " + name);
        RequestHandler<?, ?> handler = (RequestHandler<?, ?>) applicationContext.getBean(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handler.getClass(), RequestHandler.class);
        if (generics != null) {
            Class<? extends Request<?>> requestType = (Class<? extends Request<?>>) generics[0];
            if (requestRegistry.containsKey(requestType)) {
                throw new DuplicateRequestHandlerRegistrationException(requestType.getCanonicalName() + " already has a registered handler. Each request must have a single request handler");
            }
            RequestHandlerProvider requestProvider = new RequestHandlerProvider(applicationContext, handler.getClass());
            requestRegistry.put(requestType, requestProvider);
            logger.info("Registered RequestHandler " + handler.getClass().getSimpleName() + " to handle Request " + requestType.getSimpleName());
        }
    }

    private void registerEventHandler(String name) {
        logger.debug("Registering EventHandler with name " + name);
        EventHandler<?> eventHandler = (EventHandler<?>) applicationContext.getBean(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(eventHandler.getClass(), EventHandler.class);
        if (generics != null) {
            Class<? extends Event> eventType = (Class<? extends Event>) generics[0];
            EventHandlerProvider eventProvider = new EventHandlerProvider(applicationContext, eventHandler.getClass());
            Set<EventHandlerProvider<?>> providers = eventRegistry.get(eventType);
            if (providers != null) {
                providers.add(eventProvider);
            } else {
                providers = new HashSet<>();
                providers.add(eventProvider);
                eventRegistry.put(eventType, providers);
            }
            logger.info("Register EventHandler " + eventHandler.getClass().getSimpleName() + " to handle Event " + eventType.getSimpleName());
        }
    }

    private void registerCommandHandler(String name) {
        logger.debug("Registering CommandHandler with name " + name);
        CommandHandler<?> commandHandler = (CommandHandler<?>) applicationContext.getBean(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(commandHandler.getClass(), CommandHandler.class);
        if (generics != null) {
            Class<? extends Command> commandType = (Class<? extends Command>) generics[0];
            if (commandRegistry.containsKey(commandType)) {
                throw new DuplicateCommandHandlerRegistrationException(commandType.getCanonicalName() + " already has a registered handler. Each command must have a single command handler");
            }
            CommandHandlerProvider commandHandlerProvider = new CommandHandlerProvider(applicationContext, commandHandler.getClass());
            commandRegistry.put(commandType, commandHandlerProvider);
            logger.info("Registered CommandHandler " + commandHandler.getClass().getSimpleName() + " to handle Command " + commandType.getSimpleName());
        }
    }


}