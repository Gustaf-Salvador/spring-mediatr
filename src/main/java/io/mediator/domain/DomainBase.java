package io.mediator.domain;

import java.util.concurrent.CompletableFuture;

import io.mediator.core.Command;
import io.mediator.core.Mediator;

public interface DomainBase {

    void addDomainEvent(Command eventItem);

    void removeDomainEvent(Command eventItem);

    void clearDomainEvents();

    void raiseDomainEvents(Mediator mediator);

    CompletableFuture<Void> raiseDomainEventsAsync(Mediator mediator);
}
