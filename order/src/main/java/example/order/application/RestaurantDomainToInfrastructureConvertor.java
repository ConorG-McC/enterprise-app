package example.order.application;

import example.order.api.BaseRestaurant;
import example.order.domain.Restaurant;
import example.order.infrastructure.MenuItem;

public class RestaurantDomainToInfrastructureConvertor {
    public static BaseRestaurant convert(Restaurant restaurant){
        //Map to infrastructure
        BaseRestaurant r =
                example.order.infrastructure.Restaurant.restaurantOf(restaurant.id().toString(),
                                                                    restaurant.name());

        //Convert all menu items to entities
        for(example.order.domain.MenuItem menuItemValueObject : restaurant.menuItems()) {
                    r.addMenuItem(new MenuItem(menuItemValueObject.id(),
                            menuItemValueObject.name(),
                            menuItemValueObject.price().asDouble(),
                            restaurant.id().toString()));
        }
        return r;
     }
}
