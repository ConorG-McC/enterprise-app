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
//Restaurant api requires a valid identity for any request to work
public class ProjectControllerTests {
    private String API_BASE_URL = "http://localhost:8901/project/";
    private String MOCK_ADMIN_TOKEN = "Admin";
    private String VALID_PROJECT_ID = "1234";

    private MockMvc mockMvc;
    private ProjectQueryHandler projectQueryHandler;
    private ProjectApplicationService projectApplicationService;
    private IdentityService identityService;

    private Project createValidRestaurantWithTasks(){
        List<BaseTask> menuItems = new ArrayList<>();
        //Need to use the factory method (not constructor)
        Project project =  Project.projectOf(VALID_PROJECT_ID,"project name");
        project.addTask(createValidMenuItem());
        return project;
    }

    private Task createValidMenuItem(){
        Task newItem = new Task();
        newItem.setId(1L);
        newItem.setName("New Item");
        newItem.setHours(1.5);
        return newItem;
    }

    @BeforeEach
    void setUp() {
        projectQueryHandler = mock(ProjectQueryHandler.class);
        projectApplicationService = mock(ProjectApplicationService.class);
        identityService = mock(IdentityService.class);

        ProjectController sut = new ProjectController(projectQueryHandler, projectApplicationService, identityService);
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                                .build();
    }

    @Test
    @DisplayName("Pass a valid project id to view a particular project's details, display the response in JSON")
    void test01() throws Exception {
        //Return type from RestaurantQueryHandler for getRestaurant is GetRestaurantResponse
        GetProjectResponse restaurantResponse = new GetProjectResponse();
        restaurantResponse.setProjectId(VALID_PROJECT_ID);
        restaurantResponse.setName("project");
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProject(VALID_PROJECT_ID)).thenReturn(Optional.of(restaurantResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat(VALID_PROJECT_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId").value(restaurantResponse.getProjectId()))
                .andExpect(jsonPath("name", equalTo(restaurantResponse.getName()))
                );
    }

    @Test
    @DisplayName("View all restaurants and display the response in JSON when a valid restaurant id is submitted")
    void test02() throws Exception {
        //Return type from RestaurantQueryHandler for getAllRestaurants is Iterable<BaseRestaurant>
        BaseProject restaurant = createValidRestaurantWithTasks();
        List<BaseProject> restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getAllProjects()).thenReturn(List.of(restaurant));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("all"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(restaurant.getId()))
                .andExpect(jsonPath("$[0].name", equalTo(restaurant.getName())))
                //expected 1L was 1 (so need to cast from long to int
                .andExpect(jsonPath("$[0].tasks[0].id", equalTo((int) restaurant.getTasks().get(0).getId())))
                .andExpect(jsonPath("$[0].tasks[0].name", equalTo(restaurant.getTasks().get(0).getName())))
                .andExpect(jsonPath("$[0].tasks[0].hours", equalTo(restaurant.getTasks().get(0).getHours()))
                );
    }

    @Test
    @DisplayName("Pass a valid restaurant id to view a particular restaurant's menu items, display the response in JSON")
    void test03() throws Exception {
        //Return type from RestaurantQueryHandler for getRestaurantMenu is GetRestaurantMenuResponse
        GetProjectTaskResponse restaurantMenuResponse = new GetProjectTaskResponse();
        restaurantMenuResponse.setProjectId(VALID_PROJECT_ID);
        restaurantMenuResponse.setName("project");
        restaurantMenuResponse.setTasks(List.of(createValidMenuItem()));
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(projectQueryHandler.getProjectTasks(VALID_PROJECT_ID)).thenReturn(Optional.of(restaurantMenuResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("task/" + VALID_PROJECT_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("projectId").value(restaurantMenuResponse.getProjectId()))
                .andExpect(jsonPath("name", equalTo(restaurantMenuResponse.getName())))
                //expected 1L was 1 (so need to cast from long to int
                .andExpect(jsonPath("tasks[0].id", equalTo((int) restaurantMenuResponse.getTasks().get(0).getId())))
                .andExpect(jsonPath("tasks[0].name", equalTo(restaurantMenuResponse.getTasks().get(0).getName())))
                .andExpect(jsonPath("tasks[0].hours", equalTo(restaurantMenuResponse.getTasks().get(0).getHours()))
                );
    }

    //to be added - post request to add a restaurant (with command details)
    //invalid requests to each end point
    // TODO - Add tests for the post request to add a project and tasks
    // TODO - Add tests for invalid scenarios of each endpoint
}
