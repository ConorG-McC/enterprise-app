package example.project.application;

import example.project.api.BaseProject;
import example.project.api.BaseTask;
import example.project.domain.Project;
import example.project.infrastructure.Task;

public class ProjectDomainToInfrastructureConvertor {
    public static BaseProject convert(Project project) {
        //Map to infrastructure
        BaseProject proj =
                example.project.infrastructure.Project.projectOf(project.id().toString(),
                        project.name());

        //Convert all tasks
        // to entities
        for (BaseTask menuItemValueObject : project.tasks()) {
            Task task = new Task(menuItemValueObject.id(),
                    menuItemValueObject.name(),
                    menuItemValueObject.hours().asDouble(),
                    project.id().toString());
            proj.addTask(task);
        }
        return proj;
    }
}
