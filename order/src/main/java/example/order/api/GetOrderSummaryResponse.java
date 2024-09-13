package example.order.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter //Needed for JSON
@Setter
@EqualsAndHashCode
//No model mapper used here due to conversion
public class GetOrderSummaryResponse {
    private String id;
    private String order_state;
    private String order_total;

    //Transformation of OrderState to String in query handler to avoid JSON error
    //Transformation of orderTotal to String here to format double in JSON response
    public GetOrderSummaryResponse(String orderId, String orderState, double orderTotal) {
        this.id = orderId;
        this.order_state = orderState;
        this.order_total = String.format("%.2f", orderTotal);
    }
}