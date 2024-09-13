package example.order.application;

import example.common.domain.Identity;
import example.common.domain.Hours;
import example.order.api.BaseRestaurant;
import example.order.api.events.RestaurantCreatedEvent;
import example.order.domain.MenuItem;
import example.order.domain.Restaurant;
import example.order.infrastructure.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RestaurantApplicationService {
    private RestaurantRepository restaurantRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void addNewRestaurant(RestaurantCreatedEvent event) {
        Optional<BaseRestaurant> restaurantFromRepo =
                restaurantRepository.findByName(event.getRestaurantName());
        if(restaurantFromRepo.isPresent()){ throw new IllegalArgumentException("Restaurant name already exists");}

        //Convert menu items from event as need to pass to aggregate constructor that way + cannot use mapper
        List<MenuItem> menuItems = event.getMenuItems().stream()
                .map(menuItem -> new example.order.domain.MenuItem(
                        menuItem.id(),
                        menuItem.name(),
                        new Hours(menuItem.price().asBigDecimal())
                ))
                .collect(Collectors.toList());

        //Create aggregate to confirm valid state of values passed
        Identity restaurantId = new Identity(event.getAggregateID());
        Restaurant restaurant = Restaurant.restaurantOf(restaurantId,
                                                        event.getRestaurantName(),
                                                        menuItems);

        //Convert to entity then save
        restaurantRepository.save(RestaurantDomainToInfrastructureConvertor.convert(restaurant));
        LOG.info("restaurant and menu items added to order context " + restaurant);
    }
}
