package example.assignment.api;

import lombok.*;

import java.util.List;

@Getter //Needed for JSON
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class GetTaskAssignmentItemsResponse {

    private List<? extends BaseAssignedTaskItemValueObject> assignedTaskItems;

}
