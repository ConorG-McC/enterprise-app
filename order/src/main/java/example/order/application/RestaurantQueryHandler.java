package example.order.application;

import example.order.api.BaseRestaurant;
import example.order.api.GetRestaurantMenuResponse;
import example.order.api.GetRestaurantResponse;
import example.order.infrastructure.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantQueryHandler {
    private RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetRestaurantResponse> getRestaurant(String restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant ->
                modelMapper.map(restaurant, GetRestaurantResponse.class));
    }

    public Iterable<BaseRestaurant> getAllRestaurants() {
        return restaurantRepository.findAllRestaurants();
    }

    public Optional<GetRestaurantMenuResponse> getRestaurantMenu(String restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant ->
                modelMapper.map(restaurant, GetRestaurantMenuResponse.class));
    }
}
