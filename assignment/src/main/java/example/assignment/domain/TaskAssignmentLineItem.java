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

    private long id;
    private String name;
    private Hours hours;

    public TaskAssignmentLineItem(long id, String name, Hours hours) {
        setId(id);
        setName(name);
        setHours(hours);
    }

    public void setId(long id) {
        assertArgumentNotEmpty(id, "Task id cannot be empty");
        this.id = id;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    private void setHours(Hours hours) {
        assertArgumentNotEmpty(hours, "hours cannot be empty");
        assertValueIsGreaterThan(hours.asBigDecimal(), BigDecimal.valueOf(MINIMUM_QUANTITY),
                "hours must be greater than " + (MINIMUM_QUANTITY - 1));
        this.hours = hours;
    }


    public long taskId() {
        return id;
    }

    public String name() {
        return name;
    }

    public Hours hours() {
        return hours;
    }

}
