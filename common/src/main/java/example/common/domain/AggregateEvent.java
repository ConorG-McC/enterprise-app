package example.common.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AggregateEvent {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    //convert the event to JSON - events only responsibility is to be raised by the aggregate
    //and passed as Json in application service so having the behaviour here allows any number of events built
    //on this class to be able to be handled without knowledge of the individual attributes for mapping
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonMessage = mapper.writeValueAsString(this);  // This will include @class
        LOG.info("Event toJson: {}", jsonMessage);
        return mapper.writeValueAsString(this);
    }
}