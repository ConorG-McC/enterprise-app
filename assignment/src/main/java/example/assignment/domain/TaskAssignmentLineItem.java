package example.assignment.domain;

import example.common.domain.Hours;
import example.common.domain.IdentifiedValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode(callSuper = false)
public class TaskAssignmentLineItem extends IdentifiedValueObject {
    final int MINIMUM_QUANTITY = 1;

    private long taskId;
    private String taskName;
    private Hours hours;

    public TaskAssignmentLineItem(long taskId, String taskName, Hours hours) {
        setTaskId(taskId);
        setTaskName(taskName);
        setHours(hours);
    }

    private void setTaskId(long taskId) {
        assertArgumentNotEmpty(taskId, "Task id cannot be empty");
        this.taskId = taskId;
    }

    private void setTaskName(String taskName) {
        assertArgumentNotEmpty(taskName, "Name cannot be empty");
        this.taskName = taskName;
    }

    private void setHours(Hours hours) {
        assertArgumentNotEmpty(hours, "hours cannot be empty");
        assertValueIsGreaterThan(hours.asBigDecimal(), BigDecimal.valueOf(MINIMUM_QUANTITY),
                "hours must be greater than " + (MINIMUM_QUANTITY - 1));
        this.hours = hours;
    }


    public long taskId() {
        return taskId;
    }

    public String name() {
        return taskName;
    }

    public Hours hours() {
        return hours;
    }

}
