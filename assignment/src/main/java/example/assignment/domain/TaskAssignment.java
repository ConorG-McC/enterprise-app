package example.assignment.domain;

import example.common.domain.*;
import example.assignment.api.events.TaskState;
import lombok.ToString;

import java.util.List;
import static example.assignment.api.events.TaskState.TO_DO;

@ToString
public class TaskAssignment extends Entity {
    private String consumerId;

    private String projectId;

    private final List<TaskAssignmentLineItem> lineItems;

    private TaskState taskState;

    //Factory method
    public static TaskAssignment createOrder(Identity id,
                                             String consumerId,
                                             Project project,
                                             List<TaskAssignmentLineItem> taskAssignmentLineItems) {

       TaskAssignment taskAssignment = new TaskAssignment(id, consumerId, project.id().toString(), taskAssignmentLineItems);

       return taskAssignment;
    }

    public TaskAssignment(Identity id,
                          String consumerId,
                          String projectId,
                          List<TaskAssignmentLineItem> taskAssignmentLineItems){
        super(id);
        setConsumerId(consumerId);
        setProjectId(projectId);
        this.lineItems = taskAssignmentLineItems;
        this.taskState = TO_DO;
    }

    public String consumerId() {
        return consumerId;
    }


    public List<TaskAssignmentLineItem> taskAssignmentLineItems() {
        return lineItems;
    }

    public String restaurantId() {
        return projectId;
    }

    public TaskState state() { return taskState;}

    private void setConsumerId(String consumerId){
        assertArgumentNotEmpty(consumerId, "consumerId cannot be empty");
        this.consumerId = consumerId;
    }

    private void setProjectId(String projectId){
        assertArgumentNotEmpty(projectId, "projectId cannot be empty");
        this.projectId = projectId;
    }

    TaskAssignmentLineItem findTaskAssignmentItem(long lineItemId) {
        return lineItems.stream().filter(li -> li.taskId()==lineItemId).findFirst().get();
    }

    public void cancelOrder(){
        taskState = TaskState.NOT_REQUIRED;
    }
}

