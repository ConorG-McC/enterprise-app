package example.project.domain;

import example.common.domain.Entity;
import example.common.domain.Identity;
import example.project.api.BaseTask;
import example.project.api.events.ProjectCreatedEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Project extends Entity {
    private List<BaseTask> tasks;
    private String name;

    //Factory method
    public static Project projectOf(Identity id, String name, List<BaseTask> tasks) {
        return new Project(id, name, tasks);
    }

    public Project(Identity id, String name, List<BaseTask> tasks) {
        super(id);
        setName(name);
        setTasks(tasks);
        //Store event (tasks need converting to event type rather than domain
        event = Optional.of((new ProjectCreatedEvent(id.toString(), name, tasks)));
    }

    public String name() {
        return name;
    }

    private void setTasks(List<BaseTask> tasks) {
        assertArgumentNotEmpty(tasks, "tasks cannot be null");
        this.tasks = tasks;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    public List<BaseTask> tasks() {
        return tasks;
    }

    public boolean findTask(long taskId) {
        return tasks.stream()
                .anyMatch(task -> task.id() == taskId);
    }

    public String toString() {
        String tasksAsString = tasks.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nProject: %s, Name: %s, Tasks \n[%s]", id(), name, tasksAsString);
    }
}
