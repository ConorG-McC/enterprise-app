package example.project.api;


import example.project.application.IdentityService;
import example.project.application.ProjectApplicationService;
import example.project.application.ProjectQueryHandler;
import example.project.infrastructure.Task;
import example.project.infrastructure.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//Project api requires a valid identity for any request to work
public class ProjectControllerTests {
    private String API_BASE_URL = "http://localhost:8901/project/";
    private String MOCK_ADMIN_TOKEN = "Admin";
    private String VALID_PROJECT_ID = "1234";

    private MockMvc mockMvc;
    private ProjectQueryHandler projectQueryHandler;
    private ProjectApplicationService projectApplicationService;
    private IdentityService identityService;

    private Project createValidProjectWithTasks(){
        List<BaseTask> tasks = new ArrayList<>();
        //Need to use the factory method (not constructor)
        Project project =  Project.projectOf(VALID_PROJECT_ID,"project name");
        project.addTask(createValidTask());
        return project;
    }

    private Task createValidTask(){
        Task newTask = new Task();
        newTask.setId(1L);
        newTask.setName("New Item");
        newTask.setHours(1.5);
        return newTask;
    }

    @BeforeEach
    void setUp() {
        projectQueryHandler = mock(ProjectQueryHandler.class);
        projectApplicationService = mock(ProjectApplicationService.class);
        identityService = mock(IdentityService.class);

        ProjectController projectController = new ProjectController(projectQueryHandler, projectApplicationService, identityService);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                                .build();
    }

    @Test
    @DisplayName("Pass a valid project id to view a particular project's details, display the response in JSON")
    void test01() throws Exception {
        //Return type from ProjectQueryHandler for getProject is GetProjectResponse
        GetProjectResponse projectResponse = new GetProjectResponse();
        projectResponse.setProjectId(VALID_PROJECT_ID);
        projectResponse.setName("project");
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProject(VALID_PROJECT_ID)).thenReturn(Optional.of(projectResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat(VALID_PROJECT_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId").value(projectResponse.getProjectId()))
                .andExpect(jsonPath("name", equalTo(projectResponse.getName()))
                );
    }

    @Test
    @DisplayName("View all projects and display the response in JSON when a valid project id is submitted")
    void test02() throws Exception {
        //Return type from ProjectQueryHandler for getAllProjects is Iterable<BaseProject>
        BaseProject project = createValidProjectWithTasks();
        List<BaseProject> projects = new ArrayList<>();
        projects.add(project);
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getAllProjects()).thenReturn(List.of(project));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("all"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(project.getId()))
                .andExpect(jsonPath("$[0].name", equalTo(project.getName())))
                //expected 1L was 1 (so need to cast from long to int
                .andExpect(jsonPath("$[0].tasks[0].id", equalTo((int) project.getTasks().get(0).getId())))
                .andExpect(jsonPath("$[0].tasks[0].name", equalTo(project.getTasks().get(0).getName())))
                .andExpect(jsonPath("$[0].tasks[0].hours", equalTo(project.getTasks().get(0).getHours()))
                );
    }

    @Test
    @DisplayName("Pass a valid project id to view a particular project's menu items, display the response in JSON")
    void test03() throws Exception {
        //Return type from ProjectQueryHandler for getProjectMenu is GetProjectMenuResponse
        GetProjectTaskResponse projectMenuResponse = new GetProjectTaskResponse();
        projectMenuResponse.setProjectId(VALID_PROJECT_ID);
        projectMenuResponse.setName("project");
        projectMenuResponse.setTasks(List.of(createValidTask()));
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProjectTasks(VALID_PROJECT_ID)).thenReturn(Optional.of(projectMenuResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("task/" + VALID_PROJECT_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId").value(projectMenuResponse.getProjectId()))
                .andExpect(jsonPath("name", equalTo(projectMenuResponse.getName())))
                //expected 1L was 1 (so need to cast from long to int
                .andExpect(jsonPath("tasks[0].id", equalTo((int) projectMenuResponse.getTasks().get(0).getId())))
                .andExpect(jsonPath("tasks[0].name", equalTo(projectMenuResponse.getTasks().get(0).getName())))
                .andExpect(jsonPath("tasks[0].hours", equalTo(projectMenuResponse.getTasks().get(0).getHours()))
                );
    }

    //to be added - post request to add a project (with command details)
    //invalid requests to each end point
    // TODO - Add tests for the post request to add a project and tasks
    // TODO - Add tests for invalid scenarios of each endpoint
}
