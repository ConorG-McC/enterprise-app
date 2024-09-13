package example.assignment.application;

import example.common.domain.Identity;
import example.common.domain.Hours;
import example.assignment.domain.*;
import example.assignment.infrastructure.AssignedTaskItem;
import example.assignment.infrastructure.TaskAssignment;

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


        return example.assignment.domain.TaskAssignment.createOrder(new Identity(ta.getId()),
                                    ta.getConsumer_id(),
                                    project,
                                    orderItems);
    }
}
