package example.common.infrastructure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class RoleCustomSerializer extends JsonSerializer<Role> {
    @Override
    public void serialize(Role role, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        //We don't need the id - just the type
        jsonGenerator.writeStringField("type", role.getType());
        jsonGenerator.writeEndObject();
    }
}
