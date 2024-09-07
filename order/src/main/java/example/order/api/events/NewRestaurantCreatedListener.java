package example.order.api.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.order.application.RestaurantApplicationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component

//Listener is also known as a message handler
@RabbitListener(queues = "newRestaurant", id = "newRestaurantCreatedListener")//unique id for listener
public class NewRestaurantCreatedListener {
    private RestaurantApplicationService restaurantApplicationService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void receiver(String message) throws JsonProcessingException {
        try{
            RestaurantCreatedEvent event = new ObjectMapper().readValue(message, RestaurantCreatedEvent.class);//Issue with the config convertor
            restaurantApplicationService.addNewRestaurant(event);
        }
        catch (IllegalArgumentException e){
            LOG.error(e.getMessage());
        }
    }
}
