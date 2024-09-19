package example.assignment.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.assignment.api.BaseTaskValueObject;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "task")
@Table(name = "task")
@Setter
@Getter
@ToString
@NoArgsConstructor //needed for the convertor
@AllArgsConstructor //needed for the convertor
//interface enables GetOrderItemsResponse to avoid coupling to interface layer via MenuItemValueObject
public class Task implements BaseTaskValueObject {
    @Id
    @SequenceGenerator(name = "task_sequence",
            sequenceName = "task_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "task_sequence")
    @Column(name = "task_id")
    private long id;

    @Column(name = "task_name")
    private String name;

    @Column(name = "task_estimated_hours")
    private double hours;

    @JsonIgnore //not displayed in list when JSONing menu items
    @Column(name = "project_id")
    private String project_id;
}
