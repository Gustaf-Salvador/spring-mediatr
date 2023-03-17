package io.jkratz.mediator.spring;

import io.jkratz.mediator.core.RequestHandler;
import org.springframework.context.ApplicationContext;

public class RequestHandlerProvider<T extends RequestHandler<?, ?>> {

    private final ApplicationContext applicationContext;
    private final Class<T> type;

    public RequestHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public T getHandler() {
        return (T) applicationContext.getBean(String.valueOf(type));
    }
}
