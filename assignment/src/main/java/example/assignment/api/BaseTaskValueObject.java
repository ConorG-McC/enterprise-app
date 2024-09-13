package example.assignment.api;

//Used in GetRestaurantMenuResponse to avoid coupling of infrastructure MenuItemValueObject to api
public interface BaseTaskValueObject {
    long getId();

    String getName();

    double getHours();

    String getProject_id();
}