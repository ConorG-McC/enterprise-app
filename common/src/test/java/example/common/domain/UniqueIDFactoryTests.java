package example.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UniqueIDFactoryTests {

    @Test
    @DisplayName("When createID is called multiple times, then each ID is unique")
    void whenCreateIDCalledMultipleTimes_thenEachIDIsUnique() {
        Identity id1 = UniqueIDFactory.createID();
        Identity id2 = UniqueIDFactory.createID();

        assertNotEquals(id1, id2);  // Ensure they are not the same object
        assertNotEquals(id1.id(), id2.id());  // Ensure the ID values are different
    }

    @Test
    @DisplayName("When createID is called, then a valid UUID is returned")
    void whenCreateIDCalled_thenValidUUIDIsReturned() {
        Identity id = UniqueIDFactory.createID();

        assertDoesNotThrow(() -> {
            UUID.fromString(id.id());  // Check that the ID is a valid UUID format
        });
    }
}
