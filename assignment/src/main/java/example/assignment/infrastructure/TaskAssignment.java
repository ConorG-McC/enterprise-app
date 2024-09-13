package example.assignment.infrastructure;

import example.assignment.api.BaseTaskAssignment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name="task_assignment")//Needed for custom queries
@Table(name="task_assignment")
@ToString
@Getter
@Setter
public class TaskAssignment implements BaseTaskAssignment {
    @Id
    @Column(name="task_assignment_id")
    private String id;

    @Column(name="consumer_id")
    private String consumer_id;

    @Column(name="project_id")
    private String project_id;

    @Column(name="task_state")
    private int task_state;

    @OneToMany(mappedBy = "task_assignment_id", cascade = {CascadeType.ALL})
    private List<AssignedTaskItem> assignedTaskItem;

    protected TaskAssignment() {}
    //Needed for the convertor
    protected TaskAssignment(String id,
                             String consumer_id,
                             String project_id,
                             int task_state
                     ) {
        this.id = id;
        this.consumer_id = consumer_id;
        this.project_id = project_id;
        this.task_state = task_state;
        this.assignedTaskItem = new ArrayList<>(); //Initialise the empty list in order to allow orderLineItems to work
    }
    //Needed for the convertor
    public void addOrderItem(AssignedTaskItem assignedTaskItems) {
        assignedTaskItem.add(assignedTaskItems);
    }


    //Factory method
    public static TaskAssignment taskAssignmentOf(String id,
                                                  String consumer_id,
                                                  String restaurant_id,
                                                  int order_state) {
        return new TaskAssignment(id,
                        consumer_id,
                        restaurant_id,
                        order_state);
    }

}