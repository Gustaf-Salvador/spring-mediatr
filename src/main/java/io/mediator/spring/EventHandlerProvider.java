package io.mediator.spring;

import org.springframework.context.ApplicationContext;

import io.mediator.core.EventHandler;

public class EventHandlerProvider<T extends EventHandler<?>> extends AbstractHandlerProvider<T> {


    public EventHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
        super(applicationContext, type);
    }

}
