package example.project.api.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import example.common.domain.Hours;
import example.project.api.BaseTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonSerialize(using = TaskCustomSerializer.class)
//Version for events (based on domain MenuItems) so that reuse of this event class by another context
//is not coupled to the domain version
public class Task implements BaseTask { //Need the interface to decouple event version in Restaurant factory method
    private long id;
    private String name;
    private Hours hours;

    public long id(){return id;}

    public String name() {
        return name;
    }

    public Hours hours() {
        return hours;
    }

    public String toString(){
        return String.format("id=%s, name=%s, hours=%s", id(), name, hours);
    }
}