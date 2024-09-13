package example.order.api;

import example.order.application.RestaurantQueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/project")
public class RestaurantController {
    private RestaurantQueryHandler restaurantQueryHandler;

    //e.g. http://localhost:8900/restaurants/all
    @GetMapping(path="/all")
    public Iterable<BaseRestaurant> findAll() {
        return restaurantQueryHandler.getAllRestaurants();
    }

    //e.g. http://localhost:8900/restaurants/r1
    @GetMapping(path="/{restaurantId}")
    public ResponseEntity<GetRestaurantResponse> findById(@PathVariable String restaurantId) {
       return restaurantQueryHandler.getRestaurant(restaurantId).map(
               o -> new ResponseEntity<>(o, HttpStatus.OK))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //e.g. http://localhost:8900/restaurants/menuItems/r1
    @GetMapping(path="/menuItems/{restaurantId}")
    public ResponseEntity<GetRestaurantMenuResponse> findMenuByRestaurantId(@PathVariable String restaurantId) {
        return restaurantQueryHandler.getRestaurantMenu(restaurantId).map(
                o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}