package io.jkratz.mediator.spring;

import org.springframework.context.ApplicationContext;
import io.jkratz.mediator.core.EventHandler;

public class CommandHandlerProvider<T extends EventHandler<?>> {

    private final ApplicationContext applicationContext;
    private final Class<T> type;
    private T handler;

    public CommandHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public T getHandler() {
        if (handler == null) {
            handler = (T) applicationContext.getBean(String.valueOf(type));
        }
        return handler;
    }
}
