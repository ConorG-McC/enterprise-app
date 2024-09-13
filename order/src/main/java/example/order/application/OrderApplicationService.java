package example.order.application;

import example.common.domain.Identity;
import example.common.domain.UniqueIDFactory;
import example.order.api.AddNewOrderCommand;
import example.order.domain.*;

import example.order.infrastructure.OrderRepository;
import example.order.infrastructure.Orders;
import example.order.infrastructure.Restaurant;
import example.order.infrastructure.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderApplicationService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional //Needed to ensure save is committed
    public void addNewOrder(AddNewOrderCommand command) throws OrderingDomainException{
        try{
            example.order.domain.Restaurant restaurant = findRestaurant(command.getRestaurantId());
            //ensure that all menu items supplied for the order already exist in restaurant's list of menu items
            command.getOrderLineItems().stream()
                .forEach(orderLineItem -> {
                    if (!restaurant.findMenuItem(orderLineItem.menuItemId())) {
                        throw new IllegalArgumentException("Menu id does not exist: " + orderLineItem.menuItemId());
                    }
                });

            //Convert other info from command(request)
            DeliveryInformation deliveryInformationForOrder = new DeliveryInformation(command.getDeliveryAddress(),
                                                        LocalDateTime.parse(command.getDeliveryDateTime()));

            PaymentInformation paymentInformationForOrder = new PaymentInformation(command.getPaymentMethodId());
            List<OrderLineItem> orderItems = command.getOrderLineItems();
            Identity idOfNewOrder = UniqueIDFactory.createID();
            LOG.info("New order id is " + idOfNewOrder);

            //Pass info to aggregate to validate
            Order newOrder = Order.createOrder(idOfNewOrder,
                                                command.getConsumerId(),
                                                restaurant,
                                                deliveryInformationForOrder,
                                                paymentInformationForOrder,
                                                orderItems);
            //Convert and save
           orderRepository.save(OrderDomainToInfrastructureConvertor.convert(newOrder));
        }
        catch (IllegalArgumentException e) {
            LOG.info(e.getMessage());
            throw new OrderingDomainException(e.getMessage());
        }
    }

    public void cancelOrder(String orderId) throws OrderingDomainException {
        try{
            Optional<Orders> orderToBeCancelled = orderRepository.findById(orderId);
            if(orderToBeCancelled.isEmpty()){ throw new IllegalArgumentException("Order id does not exist");}

            example.order.domain.Restaurant restaurant = findRestaurant(orderToBeCancelled.get().getRestaurant_id());
            //Convert to domain and cancel
            Order orderToCancel = OrderInfrastructureToDomainConvertor.convert(orderToBeCancelled.get(), restaurant);
            orderToCancel.cancelOrder();
            //Convert to infrastructure and save
            orderRepository.save(OrderDomainToInfrastructureConvertor.convert(orderToCancel));
        }
        catch (IllegalArgumentException e) {
            LOG.info(e.getMessage());
            throw new OrderingDomainException(e.getMessage());
        }
    }

    private example.order.domain.Restaurant findRestaurant(String idOfRestaurant) {
        //Check restaurant id is valid
        Optional<Restaurant> restaurantFromRepo = restaurantRepository.findById(idOfRestaurant);
        if(restaurantFromRepo.isEmpty()){ throw new IllegalArgumentException("Restaurant id does not exist");}
        //convert restaurant from infrastructure to domain
        return RestaurantInfrastructureToDomainConvertor.convert(restaurantFromRepo.get());
    }
}
