package example.assignment.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor //For model mapper
public class GetProjectResponse {
    private String projectId;
    private String name;
}