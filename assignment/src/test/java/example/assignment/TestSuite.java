package example.assignment;

import example.assignment.ordering.ordering.DeliveryTests;
import example.assignment.ordering.ordering.PaymentInformationTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {DeliveryTests.class,
                PaymentInformationTests.class})
public class TestSuite {
}
