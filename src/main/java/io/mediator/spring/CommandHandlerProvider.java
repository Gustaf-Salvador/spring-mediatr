package io.mediator.spring;

import org.springframework.context.ApplicationContext;
import io.mediator.core.EventHandler;

public class CommandHandlerProvider<T extends EventHandler<?>> extends AbstractHandlerProvider<T> {

    public CommandHandlerProvider(ApplicationContext applicationContext, Class<T> type) {
        super(applicationContext, type);
    }
}
