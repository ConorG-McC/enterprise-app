package example.common.domain;

import example.common.concrete.TestIdentifiedValueObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdentifiedValueObjectTests {

    @Test
    @DisplayName("When a new IdentifiedValueObject is created, then the default ID is -1")
    void whenNewIdentifiedValueObjectCreated_thenDefaultIdIsMinusOne() {
        TestIdentifiedValueObject obj = new TestIdentifiedValueObject();
        assertEquals(-1, obj.id(), "The default ID should be -1");
    }

    @Test
    @DisplayName("When ID is set, then the correct ID is returned by the getter")
    void whenIdIsSet_thenGetterReturnsCorrectId() {
        TestIdentifiedValueObject obj = new TestIdentifiedValueObject();
        long newId = 100;

        obj.setId(newId);  // Using Lombok's generated setter
        assertEquals(newId, obj.id(), "The getter should return the ID that was set");
    }
}
