package example.order.ordering.ordering;

import example.order.domain.PaymentInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentInformationTests {

    @Test
    @DisplayName("Entering no payment id is considered invalid")
    void test01(){
        assertThrows(IllegalArgumentException.class, () -> {
            new PaymentInformation(null);
        });
    }

    @Test
    @DisplayName("Two payments with the same details should be considered equal")
    void test02(){
        final String VALID_PAYMENT_ID = "valid payment ID";
        PaymentInformation payment1 = new PaymentInformation(VALID_PAYMENT_ID);
        PaymentInformation payment2 = new PaymentInformation(VALID_PAYMENT_ID);

        assertEquals(payment1,payment2);
    }
}
