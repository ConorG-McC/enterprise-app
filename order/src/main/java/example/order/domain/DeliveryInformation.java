package example.order.domain;


import example.common.domain.Address;
import example.common.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DeliveryInformation extends ValueObject {
    private LocalDateTime deliveryTime;
    private Address deliveryAddress;

    public DeliveryInformation(Address deliveryAddress, LocalDateTime deliveryTime) {
        setDeliveryAddress(deliveryAddress);
        setDeliveryTime(deliveryTime);
    }

    private void setDeliveryAddress(Address deliveryAddress) {
        assertArgumentNotEmpty(deliveryAddress, "Delivery address cannot be empty");
        this.deliveryAddress = deliveryAddress;
    }

    private void setDeliveryTime(LocalDateTime deliveryTime) {
        assertArgumentNotEmpty(deliveryTime, "Delivery time cannot be empty");
        assertDateIsAfter(deliveryTime,
                LocalDateTime.now(),
                "Delivery time must be after " + deliveryTime);
        this.deliveryTime = deliveryTime;
    }

    public LocalDateTime deliveryTime() {
        return deliveryTime;
    }

    public Address deliveryAddress() {
        return deliveryAddress;
    }
}

