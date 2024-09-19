package example.common.domain;

import example.common.concrete.TestAssertionConcern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionConcernTests {

    private final TestAssertionConcern assertionConcern = new TestAssertionConcern();

    @Test
    @DisplayName("When valid UUID is provided, then no exception is thrown")
    void whenValidUUIDProvided_thenNoException() {
        assertDoesNotThrow(() -> assertionConcern.assertArgumentIsUUID("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    @DisplayName("When invalid UUID is provided, then IllegalArgumentException is thrown")
    void whenInvalidUUIDProvided_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertArgumentIsUUID("invalid-uuid");
        });
    }

    @Test
    @DisplayName("When string length exceeds maximum, then IllegalArgumentException is thrown")
    void whenStringLengthExceedsMax_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertArgumentLength("too long string", 5, "String exceeds maximum length");
        });
    }

    @Test
    @DisplayName("When string length is within maximum limit, then no exception is thrown")
    void whenStringLengthWithinMaxLimit_thenNoException() {
        assertDoesNotThrow(() -> {
            assertionConcern.assertArgumentLength("short", 10, "String within limit");
        });
    }

    @Test
    @DisplayName("When string length is less than the allowed range, then IllegalArgumentException is thrown")
    void whenStringLengthLessThanRange_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertArgumentLength("a", 5, 10, "String length is outside the allowed range");
        });
    }

    @Test
    @DisplayName("When string length exceeds the allowed range, then IllegalArgumentException is thrown")
    void whenStringLengthExceedsRange_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertArgumentLength("too long string", 5, 10, "String length is outside the allowed range");
        });
    }

    @Test
    @DisplayName("When string length is within the allowed range, then no exception is thrown")
    void whenStringLengthWithinRange_thenNoException() {
        assertDoesNotThrow(() -> {
            assertionConcern.assertArgumentLength("just right", 5, 15, "String length within range");
        });
    }

    @Test
    @DisplayName("When value is outside the range, then IllegalArgumentException is thrown")
    void whenValueOutsideRange_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertValueIsBetween(50L, 10L, 40L, "Value is out of range");
        });
    }

    @Test
    @DisplayName("When value is within the range, then no exception is thrown")
    void whenValueWithinRange_thenNoException() {
        assertDoesNotThrow(() -> {
            assertionConcern.assertValueIsBetween(20L, 10L, 40L, "Value within range");
        });
    }

    @Test
    @DisplayName("When BigDecimal value is less than minimum, then IllegalArgumentException is thrown")
    void whenBigDecimalLessThanMinimum_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertValueIsGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(10), "Value is less than minimum");
        });
    }

    @Test
    @DisplayName("When BigDecimal value is greater than minimum, then no exception is thrown")
    void whenBigDecimalGreaterThanMinimum_thenNoException() {
        assertDoesNotThrow(() -> {
            assertionConcern.assertValueIsGreaterThan(BigDecimal.valueOf(15), BigDecimal.valueOf(10), "Value is greater than minimum");
        });
    }

    @Test
    @DisplayName("When object is null or empty, then IllegalArgumentException is thrown")
    void whenObjectIsNullOrEmpty_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertArgumentNotEmpty(null, "Object is empty");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            assertionConcern.assertArgumentNotEmpty("", "Object is empty");
        });
    }

    @Test
    @DisplayName("When object is not null and not empty, then no exception is thrown")
    void whenObjectIsNotNullOrEmpty_thenNoException() {
        assertDoesNotThrow(() -> {
            assertionConcern.assertArgumentNotEmpty("not empty", "Object is not empty");
        });
    }
}
