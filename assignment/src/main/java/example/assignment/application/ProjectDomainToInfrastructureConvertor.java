package example.assignment.application;


import example.assignment.api.BaseProject;
import example.assignment.api.BaseTask;
import example.assignment.domain.Project;
import example.assignment.infrastructure.Task;

public class ProjectDomainToInfrastructureConvertor {
    public static BaseProject convert(Project project){
        //Map to infrastructure
         BaseProject proj =
                 example.assignment.infrastructure.Project.projectOf(project.id().toString(),
                                                                     project.name());

        //Convert all tasks
        // to entities
        for(BaseTask taskValueItem : project.tasks()) {
                Task task = new Task(taskValueItem.id(),
                    taskValueItem.name(),
                    taskValueItem.hours().asDouble(),
                    project.id().toString());
                proj.addTask(task);
        }
        return proj;
     }
}
