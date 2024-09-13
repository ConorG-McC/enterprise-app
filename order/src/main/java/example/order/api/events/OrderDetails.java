package example.order.api.events;

import example.common.domain.Hours;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails{
    private List<OrderLineItem> lineItems; //Use event version of this rather than domain
    private Hours orderTotal;

    private long restaurantId;
    private long consumerId;
}