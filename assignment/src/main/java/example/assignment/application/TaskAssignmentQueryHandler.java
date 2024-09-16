package example.assignment.application;

import example.assignment.api.GetTaskAssignmentItemsResponse;
import example.assignment.api.GetTaskAssignmentSummaryResponse;
import example.assignment.api.events.TaskState;
import example.assignment.infrastructure.AssignedTaskItem;
import example.assignment.infrastructure.TaskAssignmentRepository;
import example.assignment.infrastructure.TaskAssignment;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskAssignmentQueryHandler {
    private TaskAssignmentRepository orderRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger LOG = LoggerFactory.getLogger(getClass());


    public Optional<GetTaskAssignmentSummaryResponse> getOrderSummary(String orderId) {
        Optional<TaskAssignment> order = orderRepository.findById(orderId);
        //Take the order and map via makeGetOrderSummaryResponse if optional.isPresent() else not found
        return order.flatMap(order1 -> Optional.of(makeGetOrderSummaryResponse(order1)));
    }

    public Optional<GetTaskAssignmentItemsResponse> getAssignedTaskItems(String orderId) {
        LOG.info("TEST: AHHH orderId {} ", orderId);
        Optional<TaskAssignment> order = orderRepository.findById(orderId);
        LOG.info("TEST: ahhh is {} ", order);

        Optional<GetTaskAssignmentItemsResponse> response = order.flatMap(order1 -> Optional.of(makeGetOrderItemsResponse(order1)));
        LOG.info("TEST: response is {} ", response);
        return response;

    }

    //No mapper used as we want to convert before assigning to response to avoid coupling to infrastructure
    private GetTaskAssignmentSummaryResponse makeGetOrderSummaryResponse(TaskAssignment order) {
        return new GetTaskAssignmentSummaryResponse(order.getId(),
                                            TaskState.values()[order.getTask_state()].name());//Avoid JSON conversion error
    }

    private GetTaskAssignmentItemsResponse makeGetOrderItemsResponse(TaskAssignment taskAssignment) {
        LOG.info("Mapping TaskAssignment to GetTaskAssignmentItemsResponse: {}", taskAssignment);

        // Get the list of AssignedTaskItem from TaskAssignment
        List<AssignedTaskItem> assignedTaskItems = taskAssignment.getAssignedTaskItem();

        // Create the response object, passing the assignedTaskItems directly
        GetTaskAssignmentItemsResponse response = new GetTaskAssignmentItemsResponse(assignedTaskItems);

        LOG.info("Mapped response: {}", response);
        return response;
    }




}
