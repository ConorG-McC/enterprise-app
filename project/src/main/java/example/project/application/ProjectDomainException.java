package example.project.application;

public class ProjectDomainException extends Exception{
    private final String message;
    public ProjectDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}