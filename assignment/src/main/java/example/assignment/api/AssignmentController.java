package example.assignment.api;

import example.assignment.application.TaskAssignmentApplicationService;
import example.assignment.application.TaskAssignmentQueryHandler;
import example.assignment.application.AssignmentOfTaskDomainException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/assignments")
public class AssignmentController {
    private TaskAssignmentQueryHandler assignmentsQueryHandler;
    private TaskAssignmentApplicationService taskAssignmentApplicationService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());


    //e.g. http://localhost:8900/assignments/summary/a1
    @GetMapping(path = "/summary/{taskAssignmentId}")
    public ResponseEntity<GetTaskAssignmentSummaryResponse> getAssignmentSummary(@PathVariable String taskAssignmentId) {
        return assignmentsQueryHandler.getAssignmentSummary(taskAssignmentId).map(
                o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //e.g. http://localhost:8900/assignments/a1
    @RequestMapping(path = "/{taskAssignmentId}", method = RequestMethod.GET)
    public ResponseEntity<GetTaskAssignmentItemsResponse> getAssignmentTasks(@PathVariable String taskAssignmentId) {
        LOG.info("Get order items for {}", taskAssignmentId);
        return assignmentsQueryHandler.getAssignedTaskItems(taskAssignmentId).map(
                o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /** e.g. http://localhost:8900/assignments/newAssignment
     {
         "consumerId": "user123",
         "projectId": "p1",
         "taskAssignmentLineItems": [
             {
                 "taskId": 1,
                 "taskName": "Develop Backend API",
                 "hours": 20.50
             },
             {
                 "taskId": 2,
                 "taskName": "Design Frontend UI",
                 "hours": 12.00
             }
         ]
     }**/
    @PostMapping("/newAssignment")
    public HttpStatus addAssignment(@RequestBody AddNewAssignmentCommand command){
        try {
            taskAssignmentApplicationService.addNewAssignment(command);
            return HttpStatus.CREATED;
        }
        catch(AssignmentOfTaskDomainException e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/{taskAssignmentId}/cancel")
    public HttpStatus cancelAssignment(@PathVariable String taskAssignmentId){
        try {
            taskAssignmentApplicationService.cancelAssignment(taskAssignmentId);
            return HttpStatus.OK;
        }
        catch(AssignmentOfTaskDomainException e){
            return HttpStatus.BAD_REQUEST;
        }
    }
}