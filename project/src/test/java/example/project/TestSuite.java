package example.project;

import example.project.api.ProjectControllerTests;
import example.project.domain.TaskTests;
import example.project.domain.ProjectTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {TaskTests.class,
                ProjectTests.class,
                ProjectControllerTests.class,})
public class TestSuite {
}
