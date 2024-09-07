package example.order.api;

import java.math.BigDecimal;

//Used by GetOrderItemsResponse to avoid coupling to OrderLineItem in infrastructure
public interface BaseOrderLineItemValueObject {
    long getId();

    long getMenuItemId();

    String getName();

    int getQuantity();

    BigDecimal getPrice();

    String getOrder_id();
}
