package pl.danielkolban.itemcqrs.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.danielkolban.itemcqrs.command.controller.ItemCommandController;
import pl.danielkolban.itemcqrs.command.entity.Item;
import pl.danielkolban.itemcqrs.query.controller.ItemQueryController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ItemCommandController.class)
public class ItemTestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemCommandController itemCommandController;

    @MockBean
    private ItemQueryController itemQueryController;

    private List<Item> items;

    private Item item;

    private final String URI = "/items";

    @BeforeEach
    void setUp() {
        items = List.of(new Item(UUID.randomUUID(), "Item1"),
                new Item(UUID.randomUUID(), "Item2"),
                new Item(UUID.randomUUID(), "Item3"),
                item = new Item(UUID.randomUUID(), "Item4"));
    }

    @Test
   public void getItemsTest() throws Exception {

        Mockito.when(itemQueryController.getAllItems()).thenReturn(items);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        Assertions.assertThat(userJson).isEqualToIgnoringCase(mapper.writeValueAsString(items));
    }

    @Test
   public void createItemTest() throws Exception {

        Mockito.when(itemCommandController.addItem(Mockito.any(Item.class))).thenReturn(String.valueOf(item));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        Assertions.assertThat(userJson).isNotEmpty();
        Assertions.assertThat(userJson).isEqualToIgnoringCase(mapper.writeValueAsString(item));
    }

}
