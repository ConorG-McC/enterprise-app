package example.project.api;

import example.project.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter//Needed for JSON
@AllArgsConstructor
public class CreateProjectCommand {
    private String projectName;
    private List<Task> tasks;

    public String toString() {
        String tasksAsString = tasks.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nproject name: %s,  tasks \n[%s]",
                projectName, tasksAsString);
    }
}
