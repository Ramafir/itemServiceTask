package pl.danielkolban.itemcqrs.query.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.danielkolban.itemcqrs.command.entity.Item;
import pl.danielkolban.itemcqrs.query.queries.GetItemQuery;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/items")
public class ItemQueryController {

    public ItemQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    private final QueryGateway queryGateway;

    @GetMapping
    public List<Item> getAllItems() {
        GetItemQuery getItemsQuery = new GetItemQuery();
        return queryGateway.query(getItemsQuery,
                ResponseTypes.multipleInstancesOf(Item.class))
                .join();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Item> getItem(@PathVariable("id") UUID id) {
        return this.queryGateway.query(
                new GetItemQuery(id), ResponseTypes.instanceOf(Item.class));
    }
}
