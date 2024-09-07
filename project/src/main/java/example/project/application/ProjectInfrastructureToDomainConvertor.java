package example.project.application;

import example.common.domain.Identity;
import example.common.domain.Hours;
import example.project.api.BaseTask;
import example.project.api.BaseProject;
import example.project.domain.Project;
import example.project.infrastructure.Task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectInfrastructureToDomainConvertor {
    public static Project convert(BaseProject project) {
        //Convert all menu items from infrastructure to domain
        List<BaseTask> tasks = new ArrayList<>();
        //Convert menu items first as need to pass to constructor that way
        for (Task taskValueObject : project.getTasks()) {
            tasks.add(new example.project.domain.Task(taskValueObject.getId(),
                    taskValueObject.getName(),
                    new Hours(BigDecimal.valueOf(taskValueObject.getHours()))));
        }

        //Map to domain
        return Project.projectOf(new Identity(project.getId()),
                project.getName(),
                tasks);
    }
}
