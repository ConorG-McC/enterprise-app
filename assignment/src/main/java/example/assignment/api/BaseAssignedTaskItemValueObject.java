package example.assignment.api;

import java.math.BigDecimal;

//Used by GetOrderItemsResponse to avoid coupling to OrderLineItem in infrastructure
public interface BaseAssignedTaskItemValueObject {

    long getTask_id();

    String getTask_name();

    BigDecimal getTask_estimated_hours();

}
