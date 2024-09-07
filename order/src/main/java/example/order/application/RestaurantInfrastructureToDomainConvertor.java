package example.order.application;

import example.common.domain.Identity;
import example.common.domain.Hours;
import example.order.api.BaseRestaurant;
import example.order.domain.Restaurant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RestaurantInfrastructureToDomainConvertor {
    public static Restaurant convert(BaseRestaurant restaurant) {
        //Convert all menu items from infrastructure to domain
        List<example.order.domain.MenuItem> menuItems = new ArrayList<>();
        //Convert menu items first as need to pass to constructor that way
        for (example.order.infrastructure.MenuItem menuItemValueObject : restaurant.getMenuItems()) {
            menuItems.add(new example.order.domain.MenuItem(menuItemValueObject.getId(),
                    menuItemValueObject.getName(),
                    new Hours(BigDecimal.valueOf(menuItemValueObject.getPrice()))));
        }

        //Map to domain
        return Restaurant.restaurantOf(new Identity(restaurant.getId()),
                restaurant.getName(),
                menuItems);
    }
}
