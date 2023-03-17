package io.jkratz.mediator.core;

public interface EventHandler<TEvent extends Event> {

    void handle(TEvent tEvent);

}
