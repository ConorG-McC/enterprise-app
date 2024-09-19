package example.assignment.api;

import example.assignment.api.events.TaskState;
import example.assignment.application.AssignmentOfTaskDomainException;
import example.assignment.application.TaskAssignmentApplicationService;
import example.assignment.application.TaskAssignmentQueryHandler;
import example.assignment.api.GetTaskAssignmentItemsResponse;
import example.assignment.api.GetTaskAssignmentSummaryResponse;
import example.assignment.api.AddNewAssignmentCommand;
import example.assignment.domain.TaskAssignmentLineItem;
import example.assignment.infrastructure.AssignedTaskItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerTest {

    private static final String API_BASE_URL = "/assignments/";
    private static final String VALID_ASSIGNMENT_ID = "a1";
    private static final String NON_EXISTENT_ASSIGNMENT_ID = "a";

    private MockMvc mockMvc;
    private TaskAssignmentQueryHandler taskAssignmentQueryHandler;
    private TaskAssignmentApplicationService taskAssignmentApplicationService;

    @BeforeEach
    void setUp() {
        taskAssignmentQueryHandler = mock(TaskAssignmentQueryHandler.class);
        taskAssignmentApplicationService = mock(TaskAssignmentApplicationService.class);
        AssignmentController assignmentController = new AssignmentController(taskAssignmentQueryHandler, taskAssignmentApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
    }

    @Test
    @DisplayName("When valid task assignment ID is provided, then assignment summary is returned")
    void whenValidAssignmentIdProvided_thenSummaryIsReturned() throws Exception {
        // Prepare mock data
        GetTaskAssignmentSummaryResponse summaryResponse = new GetTaskAssignmentSummaryResponse(VALID_ASSIGNMENT_ID, TaskState.TO_DO.name());


        // Mock behavior
        when(taskAssignmentQueryHandler.getAssignmentSummary(VALID_ASSIGNMENT_ID)).thenReturn(Optional.of(summaryResponse));

        // Perform GET request
        mockMvc.perform(get(API_BASE_URL + "summary/" + VALID_ASSIGNMENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(VALID_ASSIGNMENT_ID))
                .andExpect(jsonPath("task_state").value(TaskState.TO_DO.name()));
    }

    @Test
    @DisplayName("When non-existent task assignment ID is provided, then 404 status is returned")
    void whenNonExistentAssignmentIdProvided_thenNotFoundIsReturned() throws Exception {
        // Mock behavior
        when(taskAssignmentQueryHandler.getAssignmentSummary(NON_EXISTENT_ASSIGNMENT_ID)).thenReturn(Optional.empty());

        // Perform GET request
        mockMvc.perform(get(API_BASE_URL + "summary/" + NON_EXISTENT_ASSIGNMENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When valid task assignment ID is provided, then assignment tasks are returned")
    void whenValidAssignmentIdProvided_thenTasksAreReturned() throws Exception {
        // Prepare mock data
        AssignedTaskItem taskItem1 = new AssignedTaskItem();
        taskItem1.setAssigned_task_id(1);
        taskItem1.setTask_name("Task 1");
        taskItem1.setTask_estimated_hours(BigDecimal.valueOf(5.0));
        List<AssignedTaskItem> assignedTaskItems = List.of(taskItem1);
        GetTaskAssignmentItemsResponse taskItemsResponse = new GetTaskAssignmentItemsResponse(assignedTaskItems);

        // Mock behavior
        when(taskAssignmentQueryHandler.getAssignedTaskItems(VALID_ASSIGNMENT_ID)).thenReturn(Optional.of(taskItemsResponse));

        // Perform GET request
        mockMvc.perform(get(API_BASE_URL + VALID_ASSIGNMENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("assignedTaskItems[0].assigned_task_id").value(1))
                .andExpect(jsonPath("assignedTaskItems[0].task_name").value("Task 1"))
                .andExpect(jsonPath("assignedTaskItems[0].task_estimated_hours").value(5.0));

    }

    @Test
    @DisplayName("When non-existent task assignment ID is provided for tasks, then 404 status is returned")
    void whenNonExistentAssignmentIdProvidedForTasks_thenNotFoundIsReturned() throws Exception {
        // Mock behavior
        when(taskAssignmentQueryHandler.getAssignedTaskItems(NON_EXISTENT_ASSIGNMENT_ID)).thenReturn(Optional.empty());

        // Perform GET request
        mockMvc.perform(get(API_BASE_URL + NON_EXISTENT_ASSIGNMENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When valid assignment creation command is provided, then assignment is created")
    void whenValidAssignmentCreationCommandProvided_thenAssignmentIsCreated() throws Exception {
        // Prepare mock command
        String newAssignmentJson = """
                {
                    "consumerId": "user123",
                    "projectId": "p2",
                    "taskAssignmentLineItems": [
                        {
                            "id": 4,
                            "name": "Update Homepage",
                            "hours": 5.0
                        }
                    ]
                }
                """;

        // Mock behavior
        doNothing().when(taskAssignmentApplicationService).addNewAssignment(any(AddNewAssignmentCommand.class));

        // Perform POST request
        mockMvc.perform(post(API_BASE_URL + "newAssignment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssignmentJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When invalid assignment creation command is provided, then BadRequest is returned")
    void whenInvalidAssignmentCreationCommandProvided_thenBadRequestIsReturned() throws Exception {
        // Prepare mock invalid command
        String invalidAssignmentJson = "";

        // Mock behavior
        doThrow(new AssignmentOfTaskDomainException("Invalid assignment")).when(taskAssignmentApplicationService).addNewAssignment(any(AddNewAssignmentCommand.class));

        // Perform POST request
        mockMvc.perform(post(API_BASE_URL + "newAssignment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAssignmentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When valid task assignment ID is provided for cancellation, then assignment is cancelled")
    void whenValidAssignmentIdProvided_thenAssignmentIsCancelled() throws Exception {
        // Mock behavior
        doNothing().when(taskAssignmentApplicationService).cancelAssignment(VALID_ASSIGNMENT_ID);

        // Perform POST request
        mockMvc.perform(post(API_BASE_URL + VALID_ASSIGNMENT_ID + "/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When non-existent task assignment ID is provided for cancellation, then BadRequest is returned")
    void whenNonExistentAssignmentIdProvided_thenBadRequestIsReturned() throws Exception {
        // Mock behavior
        doThrow(new AssignmentOfTaskDomainException("Invalid assignment")).when(taskAssignmentApplicationService).cancelAssignment(NON_EXISTENT_ASSIGNMENT_ID);

        // Perform POST request and verify BAD_REQUEST status is returned
        mockMvc.perform(post(API_BASE_URL + NON_EXISTENT_ASSIGNMENT_ID + "/cancel"))
                .andExpect(status().isBadRequest())  // Verify status code is 400
                .andExpect(content().string("Bad request"));  // Verify message content
    }

}
