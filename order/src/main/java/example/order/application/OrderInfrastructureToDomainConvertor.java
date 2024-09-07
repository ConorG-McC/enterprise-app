package example.order.application;

import example.common.domain.Address;
import example.common.domain.Identity;
import example.common.domain.Hours;
import example.order.domain.*;
import example.order.infrastructure.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrderInfrastructureToDomainConvertor {
    public static Order convert(Orders o, Restaurant restaurant) {
        //Convert all menu items from infrastructure to domain
        List<OrderLineItem> orderItems = new ArrayList<>();

        for (example.order.infrastructure.OrderLineItem orderLineItem : o.getOrderLineItems()) {
            orderItems.add(new OrderLineItem(orderLineItem.getMenuItemId(),
                    orderLineItem.getName(),
                    new Hours(orderLineItem.getPrice()),
                    orderLineItem.getQuantity()));
        }
        //Map from infrastructure to domain
        Address deliveryAddress = new Address(o.getDelivery_address_house_name_number(),
                                    o.getDelivery_address_street(),
                                    o.getDelivery_address_town(),
                                    o.getDelivery_address_postal_code());
        DeliveryInformation deliveryInformation = new DeliveryInformation(deliveryAddress, o.getDelivery_day_time());

        return Order.createOrder(new Identity(o.getId()),
                                    o.getConsumer_id(),
                                    restaurant,
                                    deliveryInformation,
                                    new PaymentInformation(o.getPayment_method()),
                                    orderItems);
    }
}
