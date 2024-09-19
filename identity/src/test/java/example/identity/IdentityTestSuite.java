package example.identity;

import example.identity.api.IdentityControllerTests;
import example.identity.application.UserServiceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(
        {
                IdentityControllerTests.class,
                UserServiceTests.class
        }
)
public class IdentityTestSuite {
}
