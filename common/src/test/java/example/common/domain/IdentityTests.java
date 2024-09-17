package example.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class IdentityTests {

    @Test
    @DisplayName("When no ID is passed to Identity then an IllegalArgumentException is thrown")
    void test01(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Identity("");
        });
    }

    @Test
    @DisplayName("When new identity is created it will be a valid UUID")
    void test02(){
        //Generate controlled UUID rather than rely on UniqueIDFactory dependency
        final String VALID_UUID = "0000-00-00-00-000001";
        Identity identity = new Identity(VALID_UUID);

        assertDoesNotThrow(() -> {
            UUID.fromString(identity.id());
        });
    }
}

