package io.mediator.spring;

import org.springframework.context.ApplicationContext;

public abstract class AbstractHandlerProvider<T> {

    private final ApplicationContext applicationContext;
    private final Class<T> type;
    private T handler;

    protected AbstractHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public T getHandler() {
        if (handler == null) {
            handler = applicationContext.getBean(type);
        }
        return handler;
    }
}
