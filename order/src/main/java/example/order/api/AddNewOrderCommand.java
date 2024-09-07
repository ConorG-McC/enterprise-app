package example.order.api;

import example.common.domain.Address;
import example.order.domain.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter//Needed for JSON
@AllArgsConstructor
public class AddNewOrderCommand {
    String consumerId;
    String restaurantId;
    String paymentMethodId;
    String deliveryDateTime;
    Address deliveryAddress;
    List<OrderLineItem> orderLineItems; //use interface in api to avoid coupling to domain

    public String toString(){
        String orderLineItemsAsString = orderLineItems.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nRestaurant: %s, Payment method: %s, Delivery date/time: %s, Delivery Address: %s menu items \n[%s]",
                                restaurantId,
                                paymentMethodId,
                                deliveryDateTime,
                                deliveryAddress,
                                orderLineItemsAsString);
    }
}
