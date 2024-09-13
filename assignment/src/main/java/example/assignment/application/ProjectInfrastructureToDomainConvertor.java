package example.assignment.application;



import example.common.domain.Hours;
import example.common.domain.Identity;
import example.assignment.api.BaseProject;
import example.assignment.api.BaseTask;
import example.assignment.domain.Project;
import example.assignment.infrastructure.Task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectInfrastructureToDomainConvertor {
    public static Project convert(BaseProject project) {
        //Convert all menu items from infrastructure to domain
        List<BaseTask> tasks = new ArrayList<>();
        //Convert menu items first as need to pass to constructor that way
        for (Task taskValueObject : project.getTasks()) {
            tasks.add(new example.assignment.domain.Task(taskValueObject.getId(),
                    taskValueObject.getName(),
                    new Hours(BigDecimal.valueOf(taskValueObject.getHours()))));
        }

        //Map to domain
        return Project.projectOf(new Identity(project.getId()),
                project.getName(),
                tasks);
    }
}
