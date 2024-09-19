package example.common.domain;

import example.common.concrete.TestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTests {

    private Identity VALID_ID = new Identity("1234-5678");
    private Identity ANOTHER_ID = new Identity("9876-5432");

    @Test
    @DisplayName("When a valid Identity is provided, then Entity is created successfully")
    void whenValidIdentityProvided_thenEntityIsCreatedSuccessfully() {
        assertDoesNotThrow(() -> {
            new TestEntity(VALID_ID);
        });
    }

    @Test
    @DisplayName("When null Identity is provided, then IllegalArgumentException is thrown")
    void whenNullIdentityProvided_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TestEntity(null);
        });
    }

    @Test
    @DisplayName("When two Entities have the same Identity, then they are considered equal")
    void whenEntitiesHaveSameIdentity_thenTheyAreEqual() {
        TestEntity entity1 = new TestEntity(VALID_ID);
        TestEntity entity2 = new TestEntity(VALID_ID);

        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("When two Entities have different Identities, then they are not considered equal")
    void whenEntitiesHaveDifferentIdentities_thenTheyAreNotEqual() {
        TestEntity entity1 = new TestEntity(VALID_ID);
        TestEntity entity2 = new TestEntity(ANOTHER_ID);

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("When Entity is created, then the correct Identity is returned by id()")
    void whenEntityCreated_thenCorrectIdentityIsReturned() {
        TestEntity entity = new TestEntity(VALID_ID);
        assertEquals(VALID_ID, entity.id());
    }

    @Test
    @DisplayName("When Entity is created, then event is initialized as empty")
    void whenEntityCreated_thenEventIsEmpty() {
        TestEntity entity = new TestEntity(VALID_ID);
        assertTrue(entity.getEvent().isEmpty());
    }
}