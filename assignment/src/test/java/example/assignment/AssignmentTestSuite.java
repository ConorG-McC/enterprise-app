package example.assignment;

import example.assignment.api.AssignmentControllerTests;
import example.assignment.api.ProjectControllerTest;
import example.assignment.domain.ProjectTests;
import example.assignment.domain.TaskAssignmentLineItemTests;
import example.assignment.domain.TaskAssignmentTests;
import example.assignment.domain.TaskTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(
        {
                ProjectTests.class,
                TaskTests.class,
                TaskAssignmentTests.class,
                TaskAssignmentLineItemTests.class,
                ProjectControllerTest.class,
                AssignmentControllerTests.class

        }
)
public class AssignmentTestSuite {
}
