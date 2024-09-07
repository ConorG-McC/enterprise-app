package example.common;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {AddressTests.class,
                IdentityTests.class})
public class TestSuite {
}
