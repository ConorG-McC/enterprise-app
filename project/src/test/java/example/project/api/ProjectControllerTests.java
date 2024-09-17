package example.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import example.common.domain.Hours;
import example.project.application.IdentityService;
import example.project.application.ProjectApplicationService;
import example.project.application.ProjectDomainException;
import example.project.application.ProjectQueryHandler;
import example.project.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTests {

    private static final String API_BASE_URL = "/project/";
    private static final String MOCK_ADMIN_TOKEN = "Admin";
    private static final String MOCK_USER_TOKEN = "User";
    private static final String INVALID_TOKEN = "invalid-token";
    private static final String VALID_PROJECT_ID = "1234";
    private static final String VALID_PROJECT_NAME = "project";
    private static final String NON_EXISTENT_PROJECT_ID = "non-existent-id";

    private MockMvc mockMvc;
    private ProjectQueryHandler projectQueryHandler;
    private ProjectApplicationService projectApplicationService;
    private IdentityService identityService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        projectQueryHandler = mock(ProjectQueryHandler.class);
        projectApplicationService = mock(ProjectApplicationService.class);
        identityService = mock(IdentityService.class);

        // Initialize and configure the ObjectMapper
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ProjectController projectController = new ProjectController(projectQueryHandler, projectApplicationService, identityService);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    private example.project.infrastructure.Project createValidProjectWithTasks() {
        example.project.infrastructure.Project project = example.project.infrastructure.Project.projectOf(VALID_PROJECT_ID, VALID_PROJECT_NAME);
        project.addTask(createValidTask());
        return project;
    }

    private example.project.infrastructure.Task createValidTask() {
        example.project.infrastructure.Task newTask = new example.project.infrastructure.Task();
        newTask.setId(1L);
        newTask.setName("New Item");
        newTask.setHours(1.5);
        return newTask;
    }

    @Test
    @DisplayName("When a valid admin token and project ID are provided, then the project details are returned")
    void whenValidAdminTokenAndProjectIdProvided_thenProjectDetailsAreReturned() throws Exception {
        // Prepare mock response
        GetProjectResponse projectResponse = new GetProjectResponse();
        projectResponse.setProjectId(VALID_PROJECT_ID);
        projectResponse.setName(VALID_PROJECT_NAME);

        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProject(VALID_PROJECT_ID)).thenReturn(Optional.of(projectResponse));

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + VALID_PROJECT_ID)
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId").value(projectResponse.getProjectId()))
                .andExpect(jsonPath("name", equalTo(projectResponse.getName())));
    }

    @Test
    @DisplayName("When a valid admin token is provided but project ID does not exist, then a 404 status is returned")
    void whenValidAdminTokenButProjectIdDoesNotExist_thenNotFoundIsReturned() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProject(NON_EXISTENT_PROJECT_ID)).thenReturn(Optional.empty());

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + NON_EXISTENT_PROJECT_ID)
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When a valid admin token is provided, then all projects are returned")
    void whenValidAdminTokenProvided_thenAllProjectsAreReturned() throws Exception {
        // Prepare mock response
        BaseProject project = createValidProjectWithTasks();
        List<BaseProject> projects = new ArrayList<>();
        projects.add(project);

        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getAllProjects()).thenReturn(projects);

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "all")
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(project.getId()))
                .andExpect(jsonPath("$[0].name", equalTo(project.getName())))
                .andExpect(jsonPath("$[0].tasks[0].id", equalTo((int) project.getTasks().get(0).getId())))
                .andExpect(jsonPath("$[0].tasks[0].name", equalTo(project.getTasks().get(0).getName())))
                .andExpect(jsonPath("$[0].tasks[0].hours", equalTo(project.getTasks().get(0).getHours())));
    }

    @Test
    @DisplayName("When an invalid token is provided, then viewing all projects is unauthorized")
    void whenInvalidTokenProvided_thenViewingAllProjectsIsUnauthorized() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(INVALID_TOKEN)).thenReturn(false);

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "all")
                        .header("Authorization", INVALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("user not authorised"));
    }

    @Test
    @DisplayName("When a valid admin token and project ID are provided, then project tasks are returned")
    void whenValidAdminTokenAndProjectIdProvided_thenProjectTasksAreReturned() throws Exception {
        // Prepare mock response
        GetProjectTaskResponse projectTaskResponse = new GetProjectTaskResponse();
        projectTaskResponse.setProjectId(VALID_PROJECT_ID);
        projectTaskResponse.setName(VALID_PROJECT_NAME);
        projectTaskResponse.setTasks(List.of(createValidTask()));

        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProjectTasks(VALID_PROJECT_ID)).thenReturn(Optional.of(projectTaskResponse));

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "task/" + VALID_PROJECT_ID)
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId").value(projectTaskResponse.getProjectId()))
                .andExpect(jsonPath("name", equalTo(projectTaskResponse.getName())))
                .andExpect(jsonPath("tasks[0].id", equalTo((int) projectTaskResponse.getTasks().get(0).getId())))
                .andExpect(jsonPath("tasks[0].name", equalTo(projectTaskResponse.getTasks().get(0).getName())))
                .andExpect(jsonPath("tasks[0].hours", equalTo(projectTaskResponse.getTasks().get(0).getHours())));
    }

    @Test
    @DisplayName("When an invalid token is provided, then viewing project tasks is unauthorized")
    void whenInvalidTokenProvided_thenViewingProjectTasksIsUnauthorized() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(INVALID_TOKEN)).thenReturn(false);
        when(identityService.isSpecifiedUser(INVALID_TOKEN, VALID_PROJECT_ID)).thenReturn(false);

        // Perform the GET request
        mockMvc.perform(get(API_BASE_URL + "task/" + VALID_PROJECT_ID)
                        .header("Authorization", INVALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("user not authorised"));
    }

    @Test
    @DisplayName("When a valid admin token and valid command are provided, then a new project is created")
    void whenValidAdminTokenAndValidCommandProvided_thenNewProjectIsCreated() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectApplicationService.createProjectWithTasks(any(CreateProjectCommand.class)))
                .thenReturn(VALID_PROJECT_ID);

        // Prepare the tasks
        Task task1 = new Task(1L, "Task 1", new Hours(BigDecimal.valueOf(2.0)));
        Task task2 = new Task(2L, "Task 2", new Hours(BigDecimal.valueOf(3.0)));

        // Prepare the command
        CreateProjectCommand command = new CreateProjectCommand(
                "New Project",
                List.of(task1, task2)
        );

        // Serialize the command
        String jsonMessage = objectMapper.writeValueAsString(command);

        // Perform the POST request
        mockMvc.perform(post(API_BASE_URL)
                        .header("Authorization", MOCK_ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMessage))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(VALID_PROJECT_ID));
    }

    @Test
    @DisplayName("When no Authorization header is provided, then creating a project is unauthorized")
    void whenNoAuthorizationHeaderProvided_thenCreatingProjectIsUnauthorized() throws Exception {
        // Prepare the tasks
        Task task1 = new Task(1L, "Task 1", new Hours(BigDecimal.valueOf(2.0)));

        // Prepare the command
        CreateProjectCommand command = new CreateProjectCommand(
                "New Project",
                List.of(task1)
        );

        // Serialize the command
        String jsonMessage = objectMapper.writeValueAsString(command);

        // Perform the POST request without the Authorization header
        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMessage))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When an invalid admin token is provided, then creating a project is unauthorized")
    void whenInvalidAdminTokenProvided_thenCreatingProjectIsUnauthorized() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(MOCK_USER_TOKEN)).thenReturn(false);

        // Prepare the tasks
        Task task1 = new Task(1L, "Task 1", new Hours(BigDecimal.valueOf(2.0)));

        // Prepare the command
        CreateProjectCommand command = new CreateProjectCommand(
                "New Project",
                List.of(task1)
        );

        // Serialize the command
        String jsonMessage = objectMapper.writeValueAsString(command);

        // Perform the POST request with an invalid token
        mockMvc.perform(post(API_BASE_URL)
                        .header("Authorization", MOCK_USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMessage))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("user not authorised"));
    }

    @Test
    @DisplayName("When a valid admin token and existing project ID are provided, then the project is deleted")
    void whenValidAdminTokenAndProjectIdProvided_thenProjectIsDeleted() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectApplicationService.deleteProjectById(VALID_PROJECT_ID))
                .thenReturn("Project with id " + VALID_PROJECT_ID + " deleted");

        // Perform the DELETE request
        mockMvc.perform(delete(API_BASE_URL + VALID_PROJECT_ID)
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isGone())
                .andExpect(content().string("Project with id " + VALID_PROJECT_ID + " deleted"));
    }

    @Test
    @DisplayName("When no Authorization header is provided, then deleting a project is unauthorized")
    void whenNoAuthorizationHeaderProvided_thenDeletingProjectIsUnauthorized() throws Exception {
        // Perform the DELETE request without the Authorization header
        mockMvc.perform(delete(API_BASE_URL + VALID_PROJECT_ID))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When an invalid admin token is provided, then deleting a project is unauthorized")
    void whenInvalidAdminTokenProvided_thenDeletingProjectIsUnauthorized() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(MOCK_USER_TOKEN)).thenReturn(false);

        // Perform the DELETE request with an invalid token
        mockMvc.perform(delete(API_BASE_URL + VALID_PROJECT_ID)
                        .header("Authorization", MOCK_USER_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("user not authorised"));
    }

    @Test
    @DisplayName("When a valid admin token is provided but project does not exist, then a 400 status is returned")
    void whenValidAdminTokenButProjectDoesNotExist_thenBadRequestIsReturned() throws Exception {
        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectApplicationService.deleteProjectById(NON_EXISTENT_PROJECT_ID))
                .thenThrow(new ProjectDomainException("Project not found with id " + NON_EXISTENT_PROJECT_ID));

        // Perform the DELETE request
        mockMvc.perform(delete(API_BASE_URL + NON_EXISTENT_PROJECT_ID)
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Unable to delete project: Project not found with id " + NON_EXISTENT_PROJECT_ID));
    }
}
