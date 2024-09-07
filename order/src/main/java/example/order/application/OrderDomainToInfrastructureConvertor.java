package example.order.application;

import example.order.api.BaseOrder;
import example.order.domain.Order;
import example.order.domain.OrderLineItem;
import example.order.infrastructure.Orders;
public class OrderDomainToInfrastructureConvertor {
    public static BaseOrder convert(Order order) {
        //Map to infrastructure
        Orders o = Orders.orderOf(order.id().toString(),
                order.consumerId(),
                order.restaurantId(),
                order.paymentInformation().paymentMethod(),
                order.state().ordinal(),
                order.deliveryInformation().deliveryTime(),
                order.deliveryInformation().deliveryAddress().houseNameNumber(),
                order.deliveryInformation().deliveryAddress().street(),
                order.deliveryInformation().deliveryAddress().town(),
                order.deliveryInformation().deliveryAddress().postalCode());

        //Convert all menu items to entities
        for (OrderLineItem orderLineItem : order.orderLineItems()) {
            o.addOrderItem(new example.order.infrastructure.OrderLineItem(orderLineItem.id(),
                                                                            orderLineItem.menuItemId(),
                                                                            orderLineItem.name(),
                                                                            orderLineItem.quantity(),
                                                                            orderLineItem.price().asBigDecimal(),
                                                                            order.id().toString()));

        }
        return o;
    }
}
