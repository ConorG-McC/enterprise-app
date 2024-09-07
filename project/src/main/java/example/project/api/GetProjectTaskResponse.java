package example.project.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor
public class GetProjectTaskResponse {
    private String projectId;
    private String name;
    private List<BaseTaskValueObject> tasks;
}
