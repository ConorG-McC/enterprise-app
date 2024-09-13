package example.assignment.api;

import example.assignment.infrastructure.AssignedTaskItem;

import java.util.List;

//Avoid coupling infrastructure to api
public interface BaseTaskAssignment {

    String getId();

    String getConsumer_id();

    String getProject_id();

    int getTask_state();

    List<AssignedTaskItem> getAssignedTaskItem();

}
