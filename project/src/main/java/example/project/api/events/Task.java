package example.project.api.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import example.common.domain.Hours;
import example.project.api.BaseTask;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@JsonSerialize(using = MenuItemCustomSerializer.class)
//Version for events (based on domain MenuItems) so that reuse of this event class by another context
//is not coupled to the domain version
public class Task implements BaseTask { //Need the interface to decouple event version in Restaurant factory method
    private long id;
    private String name;
    private Hours price;

    public long id(){return id;}

    public String name() {
        return name;
    }

    public Hours hours() {
        return price;
    }

    public String toString(){
        return String.format("id=%s, name=%s, price=%s", id(), name, price);
    }
}