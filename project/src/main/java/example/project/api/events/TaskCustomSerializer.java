package example.project.api.events;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import example.project.domain.Task;

import java.io.IOException;

public class TaskCustomSerializer extends JsonSerializer<Task> {
    @Override
    public void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(task.id())); //convert long to String
        jsonGenerator.writeStringField("name", task.name());
        jsonGenerator.writeNumberField("hours", task.hours().asBigDecimal());
        jsonGenerator.writeEndObject();
    }
}
