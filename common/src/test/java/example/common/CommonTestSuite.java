package example.common;

import example.common.domain.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(
        {
                EntityTests.class,
                UniqueIDFactoryTests.class,
                AggregateEventTests.class,
                IdentifiedValueObjectTests.class,
                AssertionConcernTests.class,
                HoursTests.class,
                IdentityTests.class
        }
)
public class CommonTestSuite {
}
