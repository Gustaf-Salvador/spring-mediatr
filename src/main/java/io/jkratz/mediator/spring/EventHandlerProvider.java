package io.jkratz.mediator.spring;

import io.jkratz.mediator.core.EventHandler;
import org.springframework.context.ApplicationContext;

public class EventHandlerProvider<T extends EventHandler<?>> {

    private final ApplicationContext applicationContext;
    private final Class<T> type;
    private T handler;

    public EventHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
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
