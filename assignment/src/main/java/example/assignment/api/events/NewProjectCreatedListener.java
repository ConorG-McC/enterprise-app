package example.assignment.api.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import example.assignment.api.BaseTask;
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
@RabbitListener(queues = "newProject", id = "newProjectListener")//unique id for listener
public class NewProjectCreatedListener {
    private ProjectApplicationService projectApplicationService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void receiver(String message) throws JsonProcessingException {
        try {
            LOG.info("Received message: {}", message);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new Jdk8Module()); // Ensure JDK8 support
// Register the custom deserializer
            SimpleModule module = new SimpleModule();
            module.addDeserializer(BaseTask.class, new TaskDeserializer());
            mapper.registerModule(module);
            ProjectCreatedEvent event = mapper.readValue(message, ProjectCreatedEvent.class);
            LOG.info("New project created: {}", event);
            projectApplicationService.createProjectWithTasks(event);
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal argument exception: {}", e.getMessage(), e);
        } catch (JsonProcessingException e) {
            LOG.error("JSON processing exception: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOG.error("General exception: {}", e.getMessage(), e);
        }
    }
}
