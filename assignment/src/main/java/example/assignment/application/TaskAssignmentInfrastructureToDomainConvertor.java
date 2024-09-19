package example.assignment.application;

import example.assignment.domain.Project;
import example.assignment.domain.TaskAssignmentLineItem;
import example.assignment.infrastructure.AssignedTaskItem;
import example.assignment.infrastructure.TaskAssignment;
import example.common.domain.Hours;
import example.common.domain.Identity;

import java.util.ArrayList;
import java.util.List;

public class TaskAssignmentInfrastructureToDomainConvertor {
    public static example.assignment.domain.TaskAssignment convert(TaskAssignment ta, Project project) {
        //Convert all menu items from infrastructure to domain
        List<TaskAssignmentLineItem> orderItems = new ArrayList<>();

        for (AssignedTaskItem assignedTaskItem : ta.getAssignedTaskItem()) {
            orderItems.add(new TaskAssignmentLineItem(assignedTaskItem.getTask_id(),
                    assignedTaskItem.getTask_name(),
                    new Hours(assignedTaskItem.getTask_estimated_hours())));
        }


        return example.assignment.domain.TaskAssignment.createAssignment(new Identity(ta.getId()),
                ta.getConsumer_id(),
                project,
                orderItems);
    }
}
