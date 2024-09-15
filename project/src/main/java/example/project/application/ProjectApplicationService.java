package example.project.application;

import example.common.domain.UniqueIDFactory;
import example.project.api.BaseTask;
import example.project.api.BaseProject;
import example.project.domain.Task;
import org.springframework.core.env.Environment;
import com.fasterxml.jackson.core.JsonProcessingException;
import example.common.domain.Identity;
import example.common.domain.Hours;
import example.project.api.CreateProjectCommand;
import example.project.domain.Project;
import example.project.infrastructure.ProjectRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectApplicationService {
    private ProjectRepository projectRepository;
    private Environment env;
    private RabbitTemplate sender;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public String createProjectWithTasks(CreateProjectCommand command) throws ProjectDomainException {
        try {
            Optional<BaseProject> projectFromRepo =
                    projectRepository.findByName(command.getProjectName());
            if (projectFromRepo.isPresent()) {
                throw new IllegalArgumentException("Project name already exists");
            }
            Identity idOfNewProject = UniqueIDFactory.createID();
            //Convert tasks from command as need to pass to aggregate constructor that way + cannot use mapper
            List<BaseTask> tasks = command.getTasks().stream()
                    .map(task -> new Task(
                            -1,
                            task.name(),
                            new Hours(task.hours().asBigDecimal())
                    ))
                    .collect(Collectors.toList());
            //Create aggregate to confirm valid state of values passed and creating an event (but not retrieved here)
            Project project = Project.projectOf(idOfNewProject,
                                                            command.getProjectName(),
                                                            tasks);
            //Convert to entity then save - but retrieve the entity following the save to get id
            BaseProject projectEntity = projectRepository.save(ProjectDomainToInfrastructureConvertor.convert(project));
            //Convert entity to domain to identify new task id's from command (for event)-
            //creates domain and generates event
            project = ProjectInfrastructureToDomainConvertor.convert(projectEntity);
            LOG.info("checking ids of tasks" + project);
            publishNewProjectEvent(project);//notify any subscribers

           //Return the id back to the controller
           return project.id().toString();
        }
        catch (IllegalArgumentException e) {
            throw new ProjectDomainException(e.getMessage());
        }
    }

    private void publishNewProjectEvent(Project project) throws ProjectDomainException {
        if(project.getEvent().isEmpty()) {
            throw new ProjectDomainException("New Project Event not generated by domain");
        }
        LOG.info("publishing new project event" + project);
        try{

            //send the event using the exchange and routing key
            sender.convertAndSend(Objects.requireNonNull(env.getProperty("rabbitmq.exchange")),
                    Objects.requireNonNull(env.getProperty("rabbitmq.newProjectKey")),
                    project.getEvent().get().toJson());

            //** Consider creating and saving to a local event store DB as well here **
        } catch (JsonProcessingException e) {
            LOG.error("JSON processing error sending event to RabbitMQ", e.getMessage());
        }
        catch (Exception e) {
            LOG.error("Error sending event to RabbitMQ: " + e.getMessage());
        }
    }
}