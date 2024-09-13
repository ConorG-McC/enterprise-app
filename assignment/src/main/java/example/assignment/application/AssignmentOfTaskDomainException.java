package example.assignment.application;

public class AssignmentOfTaskDomainException extends Exception{
    private final String message;
    public AssignmentOfTaskDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}