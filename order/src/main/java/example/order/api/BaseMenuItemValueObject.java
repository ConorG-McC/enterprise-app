package example.order.api;


//Used in GetRestaurantMenuResponse to avoid coupling of infrastructure MenuItemValueObject to api
public interface BaseMenuItemValueObject {
    long getId();

    String getName();

    double getPrice();

    String getRestaurant_id();
}