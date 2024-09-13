package example.common.domain;

import lombok.Getter;

import java.util.Optional;


public abstract class Entity extends AssertionConcern {
    @Getter
    protected Optional<AggregateEvent> event = Optional.empty();

    protected final Identity id;
    protected Entity(Identity id) {
        assertArgumentNotEmpty(id,"Id cannot be empty");
        this.id = id;
    }

    public boolean equals(Object o){
        if (o == null && o.getClass() != this.getClass()){
            return false;
        }
        Entity another = (Entity) o;

        return another.id == this.id;
    }
    public Identity id(){
        return id;
    }
}
