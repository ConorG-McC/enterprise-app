package example.assignment.application;

import example.common.domain.Identity;
import example.common.domain.UniqueIDFactory;
import example.assignment.api.AddNewAssignmentCommand;
import example.assignment.domain.*;

import example.assignment.infrastructure.ProjectRepository;
import example.assignment.infrastructure.TaskAssignmentRepository;
import example.assignment.infrastructure.TaskAssignment;
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
    private final TaskAssignmentRepository orderRepository;
    private final ProjectRepository projectRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional //Needed to ensure save is committed
    public void addNewOrder(AddNewAssignmentCommand command) throws AssignmentOfTaskDomainException {
        try{
            example.assignment.domain.Project project = findProject(command.getProjectId());
            //ensure that all menu items supplied for the order already exist in restaurant's list of menu items
            command.getTaskAssignmentLineItems().stream()
                .forEach(orderLineItem -> {
                    if (!project.findTask(orderLineItem.taskId())) {
                        throw new IllegalArgumentException("Menu id does not exist: " + orderLineItem.taskId());
                    }
                });


            List<TaskAssignmentLineItem> orderItems = command.getTaskAssignmentLineItems();
            Identity idOfNewOrder = UniqueIDFactory.createID();
            LOG.info("New order id is " + idOfNewOrder);

            //Pass info to aggregate to validate
            example.assignment.domain.TaskAssignment newTaskAssignment = example.assignment.domain.TaskAssignment.createOrder(idOfNewOrder,
                                                command.getConsumerId(),
                                                project,
                                                orderItems);
            //Convert and save
           orderRepository.save(TaskAssignmentDomainToInfrastructureConvertor.convert(newTaskAssignment));
        }
        catch (IllegalArgumentException e) {
            LOG.info(e.getMessage());
            throw new AssignmentOfTaskDomainException(e.getMessage());
        }
    }

    public void cancelOrder(String orderId) throws AssignmentOfTaskDomainException {
        try{
            Optional<TaskAssignment> orderToBeCancelled = orderRepository.findById(orderId);
            if(orderToBeCancelled.isEmpty()){ throw new IllegalArgumentException("Order id does not exist");}

            example.assignment.domain.Project project = findProject(orderToBeCancelled.get().getProject_id());
            //Convert to domain and cancel
            example.assignment.domain.TaskAssignment taskAssignmentToCancel = TaskAssignmentInfrastructureToDomainConvertor.convert(orderToBeCancelled.get(), project);
            taskAssignmentToCancel.cancelOrder();
            //Convert to infrastructure and save
            orderRepository.save(TaskAssignmentDomainToInfrastructureConvertor.convert(taskAssignmentToCancel));
        }
        catch (IllegalArgumentException e) {
            LOG.info(e.getMessage());
            throw new AssignmentOfTaskDomainException(e.getMessage());
        }
    }

    private example.assignment.domain.Project findProject(String idOfProject) {
        //Check restaurant id is valid
        Optional<example.assignment.infrastructure.Project> restaurantFromRepo = projectRepository.findById(idOfProject);
        if(restaurantFromRepo.isEmpty()){ throw new IllegalArgumentException("Restaurant id does not exist");}
        //convert restaurant from infrastructure to domain
        return ProjectInfrastructureToDomainConvertor.convert(restaurantFromRepo.get());
    }
}
