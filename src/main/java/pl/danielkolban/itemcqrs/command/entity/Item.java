package pl.danielkolban.itemcqrs.command.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Builder
public class Item {
    @Id
    private UUID id;
    private String name;
}
