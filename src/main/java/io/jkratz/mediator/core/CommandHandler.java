package io.jkratz.mediator.core;

import io.jkratz.mediator.core.Command;

public interface CommandHandler<TCommand extends Command> {

    void handle(TCommand tCommand);
}
