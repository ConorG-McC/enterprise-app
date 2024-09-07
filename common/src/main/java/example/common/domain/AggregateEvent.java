package example.common.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AggregateEvent {
    //convert the event to JSON - events only responsibility is to be raised by the aggregate
    //and passed as Json in application service so having the behaviour here allows any number of events built
    //on this class to be able to be handled without knowledge of the individual attributes for mapping
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}