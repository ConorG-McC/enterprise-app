package example.order.infrastructure;

import example.order.api.BaseRestaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {
    //Used in application service to avoid coupling of api to infrastructure layer
    //Needs the explicit use of @Query when returning an interface rather than the concrete type (Restaurant)
    @Query("FROM restaurant")
    Iterable<BaseRestaurant> findAllRestaurants();

    Optional<BaseRestaurant> findByName(String name);

    //Used in application service to avoid coupling of application layer to infrastructure layer
    BaseRestaurant save(BaseRestaurant restaurant);
}