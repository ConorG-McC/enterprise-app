package example.assignment.api;

import example.assignment.domain.TaskAssignmentLineItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter//Needed for JSON
@AllArgsConstructor
public class AddNewAssignmentCommand {
    String consumerId;
    String projectId;
    List<TaskAssignmentLineItem> taskAssignmentLineItems; //use interface in api to avoid coupling to domain

    public String toString() {
        String assignmentItemsAsString = taskAssignmentLineItems.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nProject: %s, tasks \n[%s]",
                projectId,
                assignmentItemsAsString);
    }
}
