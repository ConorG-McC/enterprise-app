package example.assignment.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.assignment.api.BaseAssignedTaskItemValueObject;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "assigned_task")//Needed for custom queries
@Table(name = "assigned_task")
@ToString
@Getter
@Setter
@NoArgsConstructor //needed for the convertor
@AllArgsConstructor //needed for the convertor
//interface enables GetOrderItemsResponse to avoid coupling to interface layer via OrderLineItem
public class AssignedTaskItem implements BaseAssignedTaskItemValueObject {
    @Id
    @SequenceGenerator(name = "assigned_task_sequence",
            sequenceName = "assigned_task_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "assigned_task_sequence")
    @Column(name = "assigned_task_id")
    private long assigned_task_id;

    @JsonIgnore //not displayed in JSONing menu items
    @Column(name = "task_id")
    private long task_id;

    @Column(name = "task_name")
    private String task_name;

    @Column(name = "task_estimated_hours")
    private BigDecimal task_estimated_hours;

    @JsonIgnore //not displayed in JSONing menu items
    @Column(name = "task_assignment_id")
    private String task_assignment_id;

}
