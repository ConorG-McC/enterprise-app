package example.project;

import example.project.api.ProjectControllerTests;
import example.project.application.ProjectApplicationServiceTests;
import example.project.domain.ProjectTests;
import example.project.domain.TaskTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(
        {
                TaskTests.class,
                ProjectTests.class,
                ProjectApplicationServiceTests.class,
                ProjectControllerTests.class
        }
)
public class ProjectTestSuite {
}
