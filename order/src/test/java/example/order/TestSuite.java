package example.order;

import example.order.ordering.ordering.DeliveryTests;
import example.order.ordering.ordering.PaymentInformationTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {DeliveryTests.class,
                PaymentInformationTests.class})
public class TestSuite {
}
