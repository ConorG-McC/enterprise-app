package example.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class IdentityTests {

    private String VALID_ID = "1234-5678-91011";
    private String EMPTY_ID = "";

    @Test
    @DisplayName("When no ID is passed to Identity, then IllegalArgumentException is thrown")
    void whenNoIdProvided_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Identity(EMPTY_ID);
        });
    }

    @Test
    @DisplayName("When a valid UUID is passed to Identity, then the ID is valid")
    void whenValidUUIDProvided_thenIdentityIsCreatedSuccessfully() {
        // Generate a valid UUID
        final String VALID_UUID = UUID.randomUUID().toString();
        Identity identity = new Identity(VALID_UUID);

        assertDoesNotThrow(() -> {
            UUID.fromString(identity.id()); // Verify it's a valid UUID
        });
    }

    @Test
    @DisplayName("When a valid ID is provided, then Identity is created successfully")
    void whenValidIdProvided_thenIdentityIsCreatedSuccessfully() {
        assertDoesNotThrow(() -> {
            new Identity(VALID_ID);
        });
    }

    @Test
    @DisplayName("When a valid Identity is created, then the correct ID is returned")
    void whenValidIdentityCreated_thenCorrectIdIsReturned() {
        Identity identity = new Identity(VALID_ID);
        assertEquals(VALID_ID, identity.id());
    }

    @Test
    @DisplayName("When a valid Identity is created, then toString returns correct ID")
    void whenValidIdentityCreated_thenToStringReturnsCorrectId() {
        Identity identity = new Identity(VALID_ID);
        assertEquals(VALID_ID, identity.toString());
    }
}