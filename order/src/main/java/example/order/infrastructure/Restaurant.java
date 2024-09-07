package example.order.infrastructure;

import example.order.api.BaseRestaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name="restaurant")//Needed for custom queries
@Table(name="restaurant")
@ToString
@Getter
@Setter
public class Restaurant implements BaseRestaurant {
    @Id
    @Column(name = "restaurant_id")
    private String id;

    @Column(name = "restaurant_name")
    private String name;

    @OneToMany(mappedBy = "restaurant_id", cascade = {CascadeType.ALL})
    private List<MenuItem> menuItems;

    protected Restaurant() {}
    //Needed for the convertor
    protected Restaurant(String id, String name) {
        this.id = id;
        this.name = name;
        this.menuItems = new ArrayList<>(); //Initialise the empty list in order to allow addMenuItem to work
    }
    //Needed for the convertor
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    //Factory method
    public static Restaurant restaurantOf(String id, String name) {
        return new Restaurant(id, name);
    }
}
