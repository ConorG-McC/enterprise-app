package example.order.domain;

import example.common.domain.Entity;
import example.common.domain.Identity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Restaurant extends Entity {
    private final List<MenuItem> menuItems;
    private String name;

    //Factory method
    public static Restaurant restaurantOf(Identity id, String name, List<MenuItem> menuItems) {
        return new Restaurant(id, name, menuItems);
    }

    public Restaurant(Identity id, String name, List<MenuItem> menuItems) {
        super(id);
        setName(name);
        this.menuItems = menuItems;
    }

    public String name(){
        return name;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    public List<MenuItem> menuItems(){
        return menuItems;
    }

    public boolean findMenuItem(long menuItemId){
        return menuItems.stream()
                        .anyMatch(menuItem -> menuItem.id()==menuItemId);
    }

    public String toString(){
        String menuItemsAsString = menuItems.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nRestaurant: %s, Name: %s, menu items \n[%s]", id(), name, menuItemsAsString);
    }
}
