package example.order.domain;

import example.common.domain.*;
import example.order.api.events.OrderState;
import lombok.ToString;

import java.util.List;
import static example.order.api.events.OrderState.APPROVAL_PENDING;

@ToString
public class Order extends Entity {
    private String consumerId;

    private String restaurantId;

    private DeliveryInformation deliveryInformation;

    private PaymentInformation paymentInformation;
    private final List<OrderLineItem> lineItems;

    private OrderState state;
    private final Hours minimumOrderValue = new Hours(10);

    //Factory method
    public static Order createOrder(Identity id,
                                    String consumerId,
                                    Restaurant restaurant,
                                    DeliveryInformation deliveryInformation,
                                    PaymentInformation paymentInformation,
                                    List<OrderLineItem> orderLineItems) {

       Order order = new Order(id, consumerId, restaurant.id().toString(), deliveryInformation, paymentInformation, orderLineItems);

       return order;
    }

    public Order(Identity id,
                 String consumerId,
                 String restaurantId,
                 DeliveryInformation deliveryInformation,
                 PaymentInformation paymentInformation,
                 List<OrderLineItem> orderLineItems){
        super(id);
        setConsumerId(consumerId);
        setRestaurantId(restaurantId);
        this.deliveryInformation = deliveryInformation;
        this.paymentInformation = paymentInformation;
        this.lineItems = orderLineItems;
        this.state = APPROVAL_PENDING;
    }

    public String consumerId() {
        return consumerId;
    }

    public DeliveryInformation deliveryInformation() {
        return deliveryInformation;
    }

    public List<OrderLineItem> orderLineItems() {
        return lineItems;
    }

    public PaymentInformation paymentInformation() {
        return paymentInformation;
    }

    public String restaurantId() {
        return restaurantId;
    }

    public OrderState state() { return state;}

    private void setConsumerId(String consumerId){
        assertArgumentNotEmpty(consumerId, "consumerId cannot be empty");
        this.consumerId = consumerId;
    }

    private void setRestaurantId(String restaurantId){
        assertArgumentNotEmpty(restaurantId, "restaurantId cannot be empty");
        this.restaurantId = restaurantId;
    }

    //Domain command methods
    public Hours orderTotal() {
        return lineItems.stream().map(OrderLineItem::total).reduce(Hours.ZERO, Hours::add);
    }

    OrderLineItem findOrderLineItem(long lineItemId) {
        return lineItems.stream().filter(li -> li.menuItemId()==lineItemId).findFirst().get();
    }

    public void cancelOrder(){
        state = OrderState.CANCELLED;
    }

    public boolean doesOrderMeetMinimumThreshold(){
        return orderTotal().isGreaterThanOrEqual(minimumOrderValue);
    }
}

