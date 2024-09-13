package example.project.api.events;

import example.common.domain.AggregateEvent;
import example.project.api.BaseTask;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProjectCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String projectName;
    private List<BaseTask> tasks;
}