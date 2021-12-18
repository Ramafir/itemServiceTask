package pl.danielkolban.itemcqrs.command.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.danielkolban.itemcqrs.command.events.ItemCreatedEvent;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Aggregate
public class ItemAggregate {
    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public ItemAggregate(CreateItemCommand command) {
        AggregateLifecycle.apply(new ItemCreatedEvent(command.getId(), command.getName()));
    }

    @EventSourcingHandler
    public void on(ItemCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }
}