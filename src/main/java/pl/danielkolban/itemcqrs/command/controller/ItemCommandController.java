package pl.danielkolban.itemcqrs.command.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.danielkolban.itemcqrs.command.aggregate.CreateItemCommand;
import pl.danielkolban.itemcqrs.command.entity.Item;

import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemCommandController {

    private final CommandGateway commandGateway;

    public ItemCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @PostMapping("")
    public String addItem(@RequestBody Item item) {
        CreateItemCommand createItemCommand = CreateItemCommand.builder()
                .id(UUID.randomUUID())
                .name(item.getName())
                .build();
        return commandGateway.sendAndWait(createItemCommand);


    }
}