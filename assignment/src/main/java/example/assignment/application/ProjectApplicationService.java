package example.assignment.application;

import example.assignment.api.BaseProject;
import example.assignment.api.BaseTask;
import example.assignment.api.events.ProjectCreatedEvent;
import example.assignment.domain.Project;
import example.assignment.domain.Task;
import example.assignment.infrastructure.ProjectRepository;
import example.common.domain.Hours;
import example.common.domain.Identity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectApplicationService {
    private ProjectRepository projectRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void createProjectWithTasks(ProjectCreatedEvent event) {
        Optional<BaseProject> projectFromRepo =
                projectRepository.findByName(event.getProjectName());
        if (projectFromRepo.isPresent()) {
            throw new IllegalArgumentException("Project name already exists");
        }

        //Convert tasks from command as need to pass to aggregate constructor that way + cannot use mapper
        List<BaseTask> tasks = event.getTasks().stream()
                .map(task -> new Task(
                        -1,
                        task.name(),
                        new Hours(task.hours().asBigDecimal())
                ))
                .collect(Collectors.toList());
        //Create aggregate to confirm valid state of values passed and creating an event (but not retrieved here)
        Identity idOfNewProject = new Identity(event.getAggregateID());
        Project project = Project.projectOf(idOfNewProject, event.getProjectName(), tasks);


        projectRepository.save(ProjectDomainToInfrastructureConvertor.convert(project));
    }

    public String deleteProjectById(String projectId) throws Exception {


        try {
            if (projectId == null || projectId.isEmpty()) {
                throw new ProjectNotFoundException("Project id is null or empty");
            }

            Optional<example.assignment.infrastructure.Project> projectFromRepo =
                    projectRepository.findById(projectId);
            if (projectFromRepo.isEmpty()) {
                throw new IllegalArgumentException("Project not found with id " + projectId);
            }

            projectRepository.deleteById(projectId);

            String response = "Project with id" + projectId + " deleted";
            return response;
        } catch (ProjectNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error deleting project from repository with id: " + projectId + "\n Error: " + e.getMessage());
        }
    }

}