package example.assignment.application;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String projectId) {
        super("Project not found with id " + projectId);
    }
}
