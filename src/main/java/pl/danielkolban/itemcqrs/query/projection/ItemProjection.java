package pl.danielkolban.itemcqrs.query.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import pl.danielkolban.itemcqrs.command.entity.Item;
import pl.danielkolban.itemcqrs.command.repository.ItemRepository;
import pl.danielkolban.itemcqrs.query.queries.GetItemQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemProjection {
    private final ItemRepository itemRepository;

    public ItemProjection(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @QueryHandler
    public List<Item> handleItems(){
        List<Item> itemsList=itemRepository.findAll();
        return itemsList.stream()
                .map(item -> Item
                        .builder()
                        .name(item.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @QueryHandler
    public Optional<Item> handleItem(GetItemQuery query) {
        return itemRepository.findById(query.getId());
    }

}
