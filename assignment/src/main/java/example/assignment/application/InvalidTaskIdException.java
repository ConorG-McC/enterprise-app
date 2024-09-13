package example.assignment.application;

public class InvalidTaskIdException extends RuntimeException {
    public InvalidTaskIdException(String projectId) {
        super("Task not found with id " + projectId);
    }
}
