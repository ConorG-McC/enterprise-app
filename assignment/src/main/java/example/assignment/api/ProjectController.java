package example.assignment.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import example.assignment.application.ProjectQueryHandler;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/project")
public class ProjectController {
    private ProjectQueryHandler projectQueryHandler;

    //e.g. http://localhost:8900/project/all
    @GetMapping(path = "/all")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(projectQueryHandler.getAllProjects());
    }

    //e.g. http://localhost:8900/project/r1
    @GetMapping(path = "/{projectId}")
    public ResponseEntity<?> findById(@PathVariable String projectId) {
        return projectQueryHandler.getProject(projectId).map(
                        o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //e.g. http://localhost:8901/project/task/r1
    @GetMapping(path = "/task/{projectId}")
    public ResponseEntity<?> findTasksByProjectId(@PathVariable String projectId) {
        return projectQueryHandler.getProjectTasks(projectId).map(
                        o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}