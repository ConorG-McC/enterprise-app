package example.project.api;

import example.project.application.IdentityService;
import example.project.application.ProjectApplicationService;
import example.project.application.ProjectDomainException;
import example.project.application.ProjectQueryHandler;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/project")
public class ProjectController {
    private ProjectQueryHandler projectQueryHandler;
    private ProjectApplicationService projectApplicationService;
    private IdentityService identityService;

    //e.g. http://localhost:8901/project/all
    @GetMapping(path = "/all")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        try {
            //Valid ADMIN?
            if (identityService.isAdmin(token)) {
                return ResponseEntity.ok().body(projectQueryHandler.getAllProjects());
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }

    //e.g. http://localhost:8901/project/r1
    @GetMapping(path = "/{projectId}")
    public ResponseEntity<?> findById(@PathVariable String projectId,
                                      @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token) ||
                    identityService.isSpecifiedUser(token, projectId)) {
                return projectQueryHandler.getProject(projectId).map(
                                o -> new ResponseEntity<>(o, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }

    //e.g. http://localhost:8901/project/task/r1
    @GetMapping(path = "/task/{projectId}")
    public ResponseEntity<?> findTasksByProjectId(@PathVariable String projectId,
                                                  @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token) ||
                    identityService.isSpecifiedUser(token, projectId)) {
                return projectQueryHandler.getProjectTasks(projectId).map(
                                o -> new ResponseEntity<>(o, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }

    /**
     e.g. POST http://localhost:8901/project
     {
         "projectName":"Alessi",
         "tasks":[
            {"name":"something","hours":2.4},
            {"name":"something2","hours":2.5}
         ]
     }
     **/
    @PostMapping
    public ResponseEntity<?> createProjectWithTasks(@RequestBody CreateProjectCommand command,
                                                    @RequestHeader("Authorization") String token) {
        try {
            //Valid ADMIN?
            if (identityService.isAdmin(token)) {
                return new ResponseEntity<>(projectApplicationService.createProjectWithTasks(command),
                        HttpStatus.CREATED);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (ProjectDomainException | JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create project: " + e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
        return generateErrorResponse("user not authorised");
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@RequestHeader("Authorization") String token, @PathVariable String projectId) {
        try {
            if (identityService.isAdmin(token)) {
                return new ResponseEntity<>(projectApplicationService.deleteProjectById(projectId), HttpStatus.GONE);
            }
        } catch (ProjectDomainException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to delete project: " + e);
        }
        return generateErrorResponse("user not authorised");
    }

    private ResponseEntity<?> generateErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}