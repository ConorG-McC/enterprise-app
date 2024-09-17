package example.common.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public abstract class IdentifiedValueObject extends AssertionConcern {
    private long id = -1; //surrogate id for ORM

    public long id() {
        return id;
    }
}

