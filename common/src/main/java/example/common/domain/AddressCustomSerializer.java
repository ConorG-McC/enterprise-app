package example.common.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AddressCustomSerializer extends JsonSerializer<Address> {
        public void serialize(Address address, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("houseNameNumber", address.houseNameNumber());
            jsonGenerator.writeStringField("street", address.street());
            jsonGenerator.writeStringField("town", address.town());
            jsonGenerator.writeStringField("postalCode", address.postalCode());
            jsonGenerator.writeEndObject();
        }
    }
