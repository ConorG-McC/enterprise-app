package example.common.domain;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class AssertionConcern {

    protected AssertionConcern() {
        super();
    }

    protected void assertArgumentIsUUID(String id) throws IllegalArgumentException {
        UUID.fromString(id.trim());//throws IllegalArgumentException if invalid
    }

    protected void assertArgumentLength(String aString, int aMaximum, String aMessage) {
        int length = aString.trim().length();
        if (length > aMaximum) {
            throw new IllegalArgumentException(aMessage);
        }
    }

    protected void assertArgumentLength(String aString, int aMinimum, int aMaximum, String aMessage) {
        int length = aString.trim().length();
        if (length < aMinimum || length > aMaximum) {
            throw new IllegalArgumentException(aMessage);
        }
    }

    protected void assertValueIsBetween(Long value, Long aMinimum, Long aMaximum, String aMessage) {
        if (value < aMinimum || value > aMaximum) {
            throw new IllegalArgumentException(aMessage);
        }
    }

    protected void assertValueIsGreaterThan(BigDecimal value, BigDecimal aMinimum, String aMessage) {
        if (value.compareTo(aMinimum) < 0) {
            throw new IllegalArgumentException(aMessage);
        }
    }

    protected void assertArgumentNotEmpty(Object o, String aMessage) {
        if (o == null || o.toString().trim().isEmpty()) {
            throw new IllegalArgumentException(aMessage);
        }
    }
}

