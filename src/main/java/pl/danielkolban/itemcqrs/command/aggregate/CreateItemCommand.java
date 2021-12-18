package pl.danielkolban.itemcqrs.command.aggregate;


import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateItemCommand {

    @TargetAggregateIdentifier
    private UUID id;
    private String name;

}