package example.assignment.api.events;

import example.common.domain.AggregateEvent;
import example.assignment.api.BaseTask;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProjectCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String projectName;
    private List<BaseTask> tasks;
}

