package example.identity;

import example.identity.api.IdentityControllerTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(
        {
                IdentityControllerTests.class
        }
        )
public class IdentityTestSuite {
}
