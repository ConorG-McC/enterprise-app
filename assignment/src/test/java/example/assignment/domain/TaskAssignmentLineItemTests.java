package example.assignment.domain;

import example.common.domain.Hours;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class TaskAssignmentLineItemTests {

    private static final long VALID_TASK_ID = 1L;
    private static final String VALID_TASK_NAME = "Task Name";
    private static final Hours VALID_HOURS = new Hours(BigDecimal.valueOf(5));

    @Test
    @DisplayName("When a valid TaskAssignmentLineItem is created, then no exception is thrown")
    void whenValidTaskAssignmentLineItemCreated_thenNoException() {
        assertDoesNotThrow(() -> new TaskAssignmentLineItem(VALID_TASK_ID, VALID_TASK_NAME, VALID_HOURS));
    }

    @Test
    @DisplayName("When task name is empty, then IllegalArgumentException is thrown")
    void whenEmptyTaskName_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskAssignmentLineItem(VALID_TASK_ID, "", VALID_HOURS));
    }

    @Test
    @DisplayName("When task name is null, then IllegalArgumentException is thrown")
    void whenNullTaskName_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskAssignmentLineItem(VALID_TASK_ID, null, VALID_HOURS));
    }

    @Test
    @DisplayName("When hours is null, then IllegalArgumentException is thrown")
    void whenNullHours_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskAssignmentLineItem(VALID_TASK_ID, VALID_TASK_NAME, null));
    }

    @Test
    @DisplayName("When hours is less than minimum, then IllegalArgumentException is thrown")
    void whenHoursLessThanMinimum_thenIllegalArgumentException() {
        Hours invalidHours = new Hours(BigDecimal.valueOf(0)); // Less than the minimum (1)
        assertThrows(IllegalArgumentException.class, () -> new TaskAssignmentLineItem(VALID_TASK_ID, VALID_TASK_NAME, invalidHours));
    }

    @Test
    @DisplayName("When task ID is retrieved, then the correct task ID is returned")
    void whenTaskIdRetrieved_thenCorrectTaskIdReturned() {
        TaskAssignmentLineItem item = new TaskAssignmentLineItem(VALID_TASK_ID, VALID_TASK_NAME, VALID_HOURS);
        assertEquals(VALID_TASK_ID, item.taskId());
    }

    @Test
    @DisplayName("When task name is retrieved, then the correct task name is returned")
    void whenTaskNameRetrieved_thenCorrectTaskNameReturned() {
        TaskAssignmentLineItem item = new TaskAssignmentLineItem(VALID_TASK_ID, VALID_TASK_NAME, VALID_HOURS);
        assertEquals(VALID_TASK_NAME, item.name());
    }

    @Test
    @DisplayName("When hours is retrieved, then the correct hours are returned")
    void whenHoursRetrieved_thenCorrectHoursReturned() {
        TaskAssignmentLineItem item = new TaskAssignmentLineItem(VALID_TASK_ID, VALID_TASK_NAME, VALID_HOURS);
        assertEquals(VALID_HOURS, item.hours());
    }
}
