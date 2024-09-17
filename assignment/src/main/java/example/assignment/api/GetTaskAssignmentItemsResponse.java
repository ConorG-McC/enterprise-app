package example.assignment.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter //Needed for JSON
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class GetTaskAssignmentItemsResponse {

    private List<? extends BaseAssignedTaskItemValueObject> assignedTaskItems;

}
