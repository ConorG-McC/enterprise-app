package example.order.infrastructure;

import example.order.api.BaseOrder;
import example.order.api.BaseRestaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, String> {
    //Used in application service to avoid coupling of application layer to infrastructure layer
    BaseOrder save(BaseOrder order);
}
