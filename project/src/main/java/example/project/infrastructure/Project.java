package example.project.infrastructure;

import example.project.api.BaseProject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "project")//Needed for custom queries
@Table(name = "project")
@ToString
@Getter
@Setter
public class Project implements BaseProject {
    @Id
    @Column(name = "project_id")
    private String id;

    @Column(name = "project_name")
    private String name;

    @OneToMany(mappedBy = "project_id", cascade = {CascadeType.ALL})
    private List<Task> tasks;

    protected Project() {
    }

    //Needed for the convertor
    protected Project(String id, String name) {
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>(); //Initialise the empty list in order to allow addMenuItem to work
    }

    //Needed for the convertor
    public void addTask(Task task) {
        tasks.add(task);
    }

    //Factory method
    public static Project projectOf(String id, String name) {
        return new Project(id, name);
    }
}
