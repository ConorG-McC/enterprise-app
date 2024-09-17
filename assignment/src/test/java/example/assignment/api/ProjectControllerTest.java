package example.assignment.api;

import example.assignment.application.ProjectQueryHandler;
import example.assignment.infrastructure.Project;
import example.assignment.infrastructure.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    private static final String API_BASE_URL = "/project/";
    private static final String VALID_AUTH_TOKEN = "valid-token";
    private static final String INVALID_AUTH_TOKEN = "invalid-token";
    private static final String VALID_PROJECT_ID = "1234";
    private static final String VALID_PROJECT_NAME = "Valid Project";
    private static final String NON_EXISTENT_PROJECT_ID = "non-existent-id";

    private MockMvc mockMvc;
    private ProjectQueryHandler projectQueryHandler;

    @BeforeEach
    void setUp() {
        projectQueryHandler = mock(ProjectQueryHandler.class);
        ProjectController projectController = new ProjectController(projectQueryHandler);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    @DisplayName("When a valid Authorization token is provided, then all projects are returned")
    void whenValidAuthTokenProvided_thenAllProjectsAreReturned() throws Exception {
        // Prepare mock data
        BaseProject project1 = Project.projectOf("1", "Project 1");
        BaseProject project2 = Project.projectOf("2", "Project 2");

        Iterable<BaseProject> projects = List.of(project1, project2);

        // Mock behavior
        when(projectQueryHandler.getAllProjects()).thenReturn(projects);

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "all")
                        .header("Authorization", VALID_AUTH_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].name", equalTo("Project 1")))
                .andExpect(jsonPath("$[1].id", equalTo("2")))
                .andExpect(jsonPath("$[1].name", equalTo("Project 2")));
    }

    @Test
    @DisplayName("When no Authorization token is provided, then the request is bad request")
    void whenNoAuthTokenProvided_thenRequestIsBadRequest() throws Exception {
        // Perform the GET request without Authorization header
        mockMvc.perform(get(API_BASE_URL + "all"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When a valid project ID is provided, then the project is returned")
    void whenValidProjectIdProvided_thenProjectIsReturned() throws Exception {
        // Prepare mock data
        GetProjectResponse projectResponse = new GetProjectResponse();
        projectResponse.setProjectId(VALID_PROJECT_ID);
        projectResponse.setName(VALID_PROJECT_NAME);

        // Mock behavior
        when(projectQueryHandler.getProject(VALID_PROJECT_ID)).thenReturn(Optional.of(projectResponse));

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + VALID_PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId", equalTo(VALID_PROJECT_ID)))
                .andExpect(jsonPath("name", equalTo(VALID_PROJECT_NAME)));
    }

    @Test
    @DisplayName("When a non-existent project ID is provided, then a 404 status is returned")
    void whenNonExistentProjectIdProvided_thenNotFoundIsReturned() throws Exception {
        // Mock behavior
        when(projectQueryHandler.getProject(NON_EXISTENT_PROJECT_ID)).thenReturn(Optional.empty());

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + NON_EXISTENT_PROJECT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When a valid project ID is provided, then the project's tasks are returned")
    void whenValidProjectIdProvided_thenProjectTasksAreReturned() throws Exception {
        // Prepare mock data
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        task1.setHours(5.0);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");
        task2.setHours(3.5);

        List<BaseTaskValueObject> tasks = List.of(task1, task2);

        GetProjectTaskResponse taskResponse = new GetProjectTaskResponse();
        taskResponse.setProjectId(VALID_PROJECT_ID);
        taskResponse.setName(VALID_PROJECT_NAME);
        taskResponse.setTasks(tasks);

        // Mock behavior
        when(projectQueryHandler.getProjectTasks(VALID_PROJECT_ID)).thenReturn(Optional.of(taskResponse));

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "task/" + VALID_PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId", equalTo(VALID_PROJECT_ID)))
                .andExpect(jsonPath("name", equalTo(VALID_PROJECT_NAME)))
                .andExpect(jsonPath("tasks[0].id", equalTo(1)))
                .andExpect(jsonPath("tasks[0].name", equalTo("Task 1")))
                .andExpect(jsonPath("tasks[0].hours", equalTo(5.0)))
                .andExpect(jsonPath("tasks[1].id", equalTo(2)))
                .andExpect(jsonPath("tasks[1].name", equalTo("Task 2")))
                .andExpect(jsonPath("tasks[1].hours", equalTo(3.5)));
    }

    @Test
    @DisplayName("When a non-existent project ID is provided, then a 404 status is returned")
    void whenNonExistentProjectIdProvided_thenTasksNotFoundIsReturned() throws Exception {
        // Mock behavior
        when(projectQueryHandler.getProjectTasks(NON_EXISTENT_PROJECT_ID)).thenReturn(Optional.empty());

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "task/" + NON_EXISTENT_PROJECT_ID))
                .andExpect(status().isNotFound());
    }
}
