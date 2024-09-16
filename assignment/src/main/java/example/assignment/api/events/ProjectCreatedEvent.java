package example.assignment.api.events;

import example.assignment.domain.Task;
import example.common.domain.AggregateEvent;
import example.assignment.api.BaseTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String projectName;
    private List<BaseTask> tasks;
}

