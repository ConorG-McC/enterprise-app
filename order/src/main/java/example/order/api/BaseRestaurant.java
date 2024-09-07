package example.order.api;

import example.order.infrastructure.MenuItem;

import java.util.List;

//Avoid coupling infrastructure to api
public interface BaseRestaurant {
    String getId();

    String getName();

    List<MenuItem> getMenuItems();

    void addMenuItem(MenuItem menuItem); //Not a getter but used in application layer when converting between entity and domain
}
