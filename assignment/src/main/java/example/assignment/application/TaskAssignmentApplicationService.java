package example.assignment.application;

import example.assignment.api.AddNewAssignmentCommand;
import example.assignment.domain.TaskAssignmentLineItem;
import example.assignment.infrastructure.ProjectRepository;
import example.assignment.infrastructure.TaskAssignment;
import example.assignment.infrastructure.TaskAssignmentRepository;
import example.common.domain.Identity;
import example.common.domain.UniqueIDFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskAssignmentApplicationService {
    private final TaskAssignmentRepository assignmentRepository;
    private final ProjectRepository projectRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional //Needed to ensure save is committed
    public void addNewAssignment(AddNewAssignmentCommand command) throws AssignmentOfTaskDomainException {
        try {
            example.assignment.domain.Project project = findProject(command.getProjectId());
            //ensure that all menu items supplied for the order already exist in restaurant's list of menu items
            command.getTaskAssignmentLineItems().stream()
                    .forEach(assignmentLineItem -> {
                        if (!project.findTask(assignmentLineItem.taskId())) {
                            throw new IllegalArgumentException("Task id does not exist: " + assignmentLineItem.taskId());
                        }
                    });


            List<TaskAssignmentLineItem> orderItems = command.getTaskAssignmentLineItems();
            Identity idOfNewAssignment = UniqueIDFactory.createID();
            LOG.info("New order id is " + idOfNewAssignment);

            //Pass info to aggregate to validate
            example.assignment.domain.TaskAssignment newTaskAssignment = example.assignment.domain.TaskAssignment.createAssignment(idOfNewAssignment,
                    command.getConsumerId(),
                    project,
                    orderItems);
            //Convert and save
            assignmentRepository.save(TaskAssignmentDomainToInfrastructureConvertor.convert(newTaskAssignment));
        } catch (IllegalArgumentException e) {
            LOG.info(e.getMessage());
            throw new AssignmentOfTaskDomainException(e.getMessage());
        }
    }

    public void cancelAssignment(String assignmentId) throws AssignmentOfTaskDomainException {
        try {
            Optional<TaskAssignment> assignmentToBeCancelled = assignmentRepository.findById(assignmentId);
            if (assignmentToBeCancelled.isEmpty()) {
                throw new IllegalArgumentException("Assignment id does not exist");
            }

            example.assignment.domain.Project project = findProject(assignmentToBeCancelled.get().getProject_id());
            //Convert to domain and cancel
            example.assignment.domain.TaskAssignment taskAssignmentToCancel = TaskAssignmentInfrastructureToDomainConvertor.convert(assignmentToBeCancelled.get(), project);
            taskAssignmentToCancel.cancelAssignment();
            //Convert to infrastructure and save
            assignmentRepository.save(TaskAssignmentDomainToInfrastructureConvertor.convert(taskAssignmentToCancel));
        } catch (IllegalArgumentException e) {
            LOG.info(e.getMessage());
            throw new AssignmentOfTaskDomainException(e.getMessage());
        }
    }

    private example.assignment.domain.Project findProject(String idOfProject) {
        //Check project id is valid
        Optional<example.assignment.infrastructure.Project> projectFromRepo = projectRepository.findById(idOfProject);
        if (projectFromRepo.isEmpty()) {
            throw new IllegalArgumentException("Project id does not exist");
        }
        //convert project from infrastructure to domain
        return ProjectInfrastructureToDomainConvertor.convert(projectFromRepo.get());
    }
}
