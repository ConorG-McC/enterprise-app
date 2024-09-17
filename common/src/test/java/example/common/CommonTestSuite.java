package example.common;

import example.common.domain.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(
        {
                HoursTests.class,
                EntityTests.class,
                UniqueIDFactoryTests.class,
                AggregateEventTests.class,
                AssertionConcernTests.class,
                IdentityTests.class
        }
)
public class CommonTestSuite {
}
