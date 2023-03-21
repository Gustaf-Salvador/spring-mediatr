package io.mediator.spring;

import org.springframework.context.ApplicationContext;

import io.mediator.core.RequestHandler;

public class RequestHandlerProvider<T extends RequestHandler<?, ?>> extends AbstractHandlerProvider<T> {

    public RequestHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
        super(applicationContext, type);
    }
}
