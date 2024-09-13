package example.assignment.application;

import example.assignment.api.BaseTaskAssignment;
import example.assignment.domain.TaskAssignmentLineItem;
import example.assignment.infrastructure.AssignedTaskItem;
import example.assignment.infrastructure.TaskAssignment;
public class TaskAssignmentDomainToInfrastructureConvertor {
    public static BaseTaskAssignment convert(example.assignment.domain.TaskAssignment taskAssignment) {
        //Map to infrastructure
        TaskAssignment o = TaskAssignment.taskAssignmentOf(taskAssignment.id().toString(),
                taskAssignment.consumerId(),
                taskAssignment.restaurantId(),
                taskAssignment.state().ordinal());

        //Convert all menu items to entities
        for (TaskAssignmentLineItem taskAssignmentLineItem : taskAssignment.taskAssignmentLineItems()) {
            o.addOrderItem(new AssignedTaskItem(taskAssignmentLineItem.id(),
                                                                            taskAssignmentLineItem.taskId(),
                                                                            taskAssignmentLineItem.name(),
                                                                            taskAssignmentLineItem.hours().asBigDecimal(),
                                                                            taskAssignment.id().toString()));
        }
        return o;
    }
}
