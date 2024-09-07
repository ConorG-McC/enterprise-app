package example.order.api;

import example.order.infrastructure.OrderLineItem;

import java.time.LocalDateTime;
import java.util.List;

//Avoid coupling infrastructure to api
public interface BaseOrder {

    String getId();

    String getConsumer_id();

    String getRestaurant_id();

    String getPayment_method();

    int getOrder_state();

    LocalDateTime getDelivery_day_time();

    String getDelivery_address_house_name_number();

    String getDelivery_address_street();

    String getDelivery_address_town();

    String getDelivery_address_postal_code();

    List<OrderLineItem> getOrderLineItems();

}
