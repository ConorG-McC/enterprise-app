package example.order.api.events;

import example.common.domain.Address;
import example.common.domain.AggregateEvent;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent extends AggregateEvent {
    private OrderDetails orderDetails;
    private Address deliveryAddress;
    private String restaurantName;
}
