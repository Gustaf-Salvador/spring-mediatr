package io.mediator.core;

public interface CommandHandler<TCommand extends Command> {

    void handle(TCommand tCommand);
}
