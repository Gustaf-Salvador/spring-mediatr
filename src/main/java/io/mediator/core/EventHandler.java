package io.mediator.core;

public interface EventHandler<eEvent extends Event> {

    <TEvent extends Event> void handle(TEvent tEvent);

}
