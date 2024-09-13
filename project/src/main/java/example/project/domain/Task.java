package example.project.domain;

import example.common.domain.Hours;
import example.common.domain.ValueObject;
import example.project.api.BaseTask;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
public class Task extends ValueObject implements BaseTask { //BaseMenuItem (lives in api to avoid coupling to domain
    private long id;
    private String name;
    private Hours hours;

    public Task(long id, String name, Hours hours) {
        setId(id);
        setName(name);
        setHours(hours);
    }

    public long id(){return id;}

    public String name() {
        return name;
    }

    public Hours hours() {
        return hours;
    }

    private void setId(long id) {
        //createRestaurantWithMenu (RestaurantApplicationService uses -1)
        assertValueIsBetween(id, -1L, Long.MAX_VALUE, "Id cannot be empty");
        this.id = id;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    private void setHours(Hours hours) {
        assertArgumentNotEmpty(hours, "hours cannot be empty");
        this.hours = hours;
    }

    public String toString(){
        return String.format("id=%s, name=%s, hours=%s", id(), name, hours);
    }

}