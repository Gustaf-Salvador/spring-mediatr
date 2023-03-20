package io.mediator.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import io.mediator.core.Command;
import io.mediator.core.Mediator;

public interface DomainBase {

    List<Command> domainEvents = new ArrayList<>();

    void addDomainEvent(Command eventItem);

    void removeDomainEvent(Command eventItem);

    void clearDomainEvents();

    void raiseDomainEvents(Mediator mediator);

    CompletableFuture<Void> raiseDomainEventsAsync(Mediator mediator);
}
