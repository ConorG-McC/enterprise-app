package example.order.api.events;

import example.common.domain.AggregateEvent;
import example.order.api.BaseMenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RestaurantCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String restaurantName;
    private List<BaseMenuItem> menuItems;
}

