package example.common.concrete;

import example.common.domain.AggregateEvent;

public class TestAggregateEvent extends AggregateEvent {
    private final String eventName;

    public TestAggregateEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
