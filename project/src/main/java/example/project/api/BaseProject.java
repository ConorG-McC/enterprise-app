package example.project.api;

import example.project.infrastructure.Task;

import java.util.List;

//Avoid coupling infrastructure to api
public interface BaseProject {
    String getId();

    String getName();

    List<Task> getTasks();

    void addTask(Task task); //Not a getter but used in application layer when converting between entity and domain
}
