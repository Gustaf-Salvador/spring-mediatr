package io.mediator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import io.mediator.core.Command;
import io.mediator.core.Mediator;
import io.mediator.core.MediatorThreadFactory;

public class AbstractDomainBase implements DomainBase {

    private List<Command> domainEvents = new ArrayList<>();

    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new MediatorThreadFactory());

    public List<Command> getDomainEvents() {
        return domainEvents;
    }

    private void setDomainEvents(List<Command> domainEvents) {
        this.domainEvents = domainEvents;
    }

    @Override
    public void addDomainEvent(Command eventItem) {
        domainEvents.add(eventItem);
    }

    @Override
    public void removeDomainEvent(Command eventItem) {
        domainEvents.remove(eventItem);
    }

    @Override
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    @Override
    public void raiseDomainEvents(Mediator mediator) {
        var domainEventList = domainEvents.stream().toList();
        clearDomainEvents();

        domainEventList.forEach(mediator::dispatch);
    }

    @Override
    public CompletableFuture<Void> raiseDomainEventsAsync(Mediator mediator) {
        return CompletableFuture.runAsync(() -> {
            var domainEventList = domainEvents.stream().toList();
            clearDomainEvents();

            domainEventList.stream().parallel().forEach(mediator::dispatch);
        }, executor);
    }
}
