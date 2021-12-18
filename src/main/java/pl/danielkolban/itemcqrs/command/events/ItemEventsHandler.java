package pl.danielkolban.itemcqrs.command.events;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.danielkolban.itemcqrs.command.entity.Item;
import pl.danielkolban.itemcqrs.command.repository.ItemRepository;
import pl.danielkolban.itemcqrs.query.queries.GetItemQuery;

import java.util.Optional;

public class ItemEventsHandler {


    private final ItemRepository itemRepository;

    public ItemEventsHandler(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @EventHandler
    public void on(ItemCreatedEvent event) throws Exception {
        Item item = new Item();
        BeanUtils.copyProperties(event, item);
        itemRepository.save(item);
        throw new Exception("Exception Occurred");
    }

    @QueryHandler
    public Optional<Item> handle(GetItemQuery query) {
        return itemRepository.findById(query.getId());
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }
}