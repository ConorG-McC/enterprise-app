package example.assignment.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter //Needed for JSON
@Setter
@EqualsAndHashCode
//No model mapper used here due to conversion
public class GetTaskAssignmentSummaryResponse {
    private String id;
    private String task_state;


    //Transformation of OrderState to String in query handler to avoid JSON error
    //Transformation of orderTotal to String here to format double in JSON response
    public GetTaskAssignmentSummaryResponse(String orderId, String orderState) {
        this.id = orderId;
        this.task_state = orderState;
    }
}