package example.assignment;

import example.assignment.api.ProjectControllerTest;
import example.assignment.domain.ProjectTests;
import example.assignment.domain.TaskTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {ProjectControllerTest.class,
                TaskTests.class,
                ProjectTests.class})
public class TestSuite {
}
