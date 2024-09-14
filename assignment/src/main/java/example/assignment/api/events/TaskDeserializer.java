package example.assignment.api.events;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import example.assignment.api.BaseTask;
import example.assignment.domain.Task;
import example.common.domain.Hours;

import java.io.IOException;
import java.math.BigDecimal;

public class TaskDeserializer extends JsonDeserializer<BaseTask> {
    @Override
    public BaseTask deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        long id = node.get("id").asLong();
        String name = node.get("name").asText();
        double hoursValue = node.get("hours").asDouble();
        Hours hours = new Hours(BigDecimal.valueOf(hoursValue));

        return new BaseTask() {
            @Override
            public long id() {
                return id;
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public Hours hours() {
                return hours;
            }
        };
    }
}
