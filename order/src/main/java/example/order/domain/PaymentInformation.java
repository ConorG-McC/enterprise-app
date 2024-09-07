package example.order.domain;

import example.common.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class PaymentInformation extends ValueObject {
    private String paymentMethodId;

    public PaymentInformation(String paymentMethodId) {
       setPaymentMethod(paymentMethodId);
    }

    private void setPaymentMethod(String paymentMethodId) {
        assertArgumentNotEmpty(paymentMethodId, "Payment method cannot be empty");
        this.paymentMethodId = paymentMethodId;
    }

    public String paymentMethod(){
        return paymentMethodId;
    }
}
