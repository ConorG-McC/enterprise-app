package example.order.application;

public class OrderingDomainException extends Exception{
    private final String message;
    public OrderingDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}