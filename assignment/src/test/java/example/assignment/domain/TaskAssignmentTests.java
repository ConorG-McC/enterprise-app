package example.assignment.domain;

import example.assignment.api.BaseTask;
import example.common.domain.Hours;
import example.common.domain.Identity;
import example.assignment.api.events.TaskState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskAssignmentTests {

    private static final Identity VALID_ID = new Identity("123e4567-e89b-12d3-a456-426614174000");
    private static final String VALID_CONSUMER_ID = "consumer-123";
    private static final String VALID_PROJECT_NAME = "Project Name";
    private static final List<BaseTask> VALID_TASKS = new ArrayList<>();

    @Test
    @DisplayName("When valid parameters are provided, then TaskAssignment is created successfully")
    void whenValidParametersProvided_thenTaskAssignmentCreatedSuccessfully() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS); // Assuming Project object

        List<TaskAssignmentLineItem> lineItems = new ArrayList<>();
        lineItems.add(new TaskAssignmentLineItem(VALID_TASKS.get(0).id(), VALID_TASKS.get(0).name(), VALID_TASKS.get(0).hours()));

        TaskAssignment assignment = TaskAssignment.createAssignment(VALID_ID, VALID_CONSUMER_ID, mockProject, lineItems);

        assertNotNull(assignment);
        assertEquals(VALID_CONSUMER_ID, assignment.consumerId());
        assertEquals(VALID_ID.toString(), assignment.projectId());
        assertEquals(lineItems, assignment.taskAssignmentLineItems());
        assertEquals(TaskState.TO_DO, assignment.state());
    }

    @Test
    @DisplayName("When consumerId is empty, then IllegalArgumentException is thrown")
    void whenConsumerIdIsEmpty_thenIllegalArgumentException() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS);

        List<TaskAssignmentLineItem> lineItems = new ArrayList<>();
        lineItems.add(new TaskAssignmentLineItem(VALID_TASKS.get(0).id(), VALID_TASKS.get(0).name(), VALID_TASKS.get(0).hours()));

        assertThrows(IllegalArgumentException.class, () -> {
            TaskAssignment.createAssignment(VALID_ID, "", mockProject, lineItems);
        });
    }

    @Test
    @DisplayName("When assignment id is empty, then IllegalArgumentException is thrown")
    void whenProjectIdIsEmpty_thenIllegalArgumentException() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS);

        List<TaskAssignmentLineItem> lineItems = new ArrayList<>();
        lineItems.add(new TaskAssignmentLineItem(VALID_TASKS.get(0).id(), VALID_TASKS.get(0).name(), VALID_TASKS.get(0).hours()));

        assertThrows(IllegalArgumentException.class, () -> {
            TaskAssignment.createAssignment(new Identity(""), VALID_CONSUMER_ID, mockProject, lineItems);
        });
    }

    @Test
    @DisplayName("When TaskAssignment is created, then the initial task state is TO_DO")
    void whenTaskAssignmentCreated_thenInitialStateIsToDo() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS);

        List<TaskAssignmentLineItem> lineItems = new ArrayList<>();
        lineItems.add(new TaskAssignmentLineItem(VALID_TASKS.get(0).id(), VALID_TASKS.get(0).name(), VALID_TASKS.get(0).hours()));

        TaskAssignment assignment = TaskAssignment.createAssignment(VALID_ID, VALID_CONSUMER_ID, mockProject, lineItems);

        assertEquals(TaskState.TO_DO, assignment.state());
    }

    @Test
    @DisplayName("When TaskAssignment is cancelled, then the state is NOT_REQUIRED")
    void whenTaskAssignmentCancelled_thenStateIsNotRequired() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS);

        List<TaskAssignmentLineItem> lineItems = new ArrayList<>();
        lineItems.add(new TaskAssignmentLineItem(VALID_TASKS.get(0).id(), VALID_TASKS.get(0).name(), VALID_TASKS.get(0).hours()));

        TaskAssignment assignment = TaskAssignment.createAssignment(VALID_ID, VALID_CONSUMER_ID, mockProject, lineItems);
        assignment.cancelAssignment();

        assertEquals(TaskState.NOT_REQUIRED, assignment.state());
    }

    @Test
    @DisplayName("When task line item is found by ID, then the correct item is returned")
    void whenTaskAssignmentItemFoundById_thenCorrectItemIsReturned() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS);

        TaskAssignmentLineItem lineItem = new TaskAssignmentLineItem(1L, "Task 1", new Hours(5));
        List<TaskAssignmentLineItem> lineItems = List.of(lineItem);

        TaskAssignment assignment = TaskAssignment.createAssignment(VALID_ID, VALID_CONSUMER_ID, mockProject, lineItems);
        TaskAssignmentLineItem foundItem = assignment.findTaskAssignmentItem(1L);

        assertEquals(lineItem, foundItem);
    }

    @Test
    @DisplayName("When task line item is not found by ID, then NoSuchElementException is thrown")
    void whenTaskAssignmentItemNotFoundById_thenNoSuchElementException() {
        VALID_TASKS.add(new Task(1L, "Task 1", new Hours(5)));
        Project mockProject = new Project(VALID_ID, VALID_PROJECT_NAME, VALID_TASKS);

        List<TaskAssignmentLineItem> lineItems = new ArrayList<>();

        TaskAssignment assignment = TaskAssignment.createAssignment(VALID_ID, VALID_CONSUMER_ID, mockProject, lineItems);

        assertThrows(java.util.NoSuchElementException.class, () -> {
            assignment.findTaskAssignmentItem(999L);
        });
    }
}
