package example.assignment.api.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import example.assignment.application.ProjectApplicationService;
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
    private ProjectApplicationService projectApplicationService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void receiver(String message) throws JsonProcessingException {
        try{
//            ProjectCreatedEvent event = new ObjectMapper().readValue(message, ProjectCreatedEvent.class);//Issue with the config convertor
//            projectApplicationService.createProjectWithTasks(event);
        }
        catch (IllegalArgumentException e){
            LOG.error(e.getMessage());
        }
    }
}
