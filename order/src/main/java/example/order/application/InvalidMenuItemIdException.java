package example.order.application;

public class InvalidMenuItemIdException extends RuntimeException {
    public InvalidMenuItemIdException(String restaurantId) {
        super("Restaurant not found with id " + restaurantId);
    }
}
