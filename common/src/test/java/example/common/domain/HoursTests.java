package example.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class HoursTests {
    private static final BigDecimal VALID_BIG_DECIMAL = BigDecimal.valueOf(5);
    private static final String VALID_STRING = "5";
    private static final int VALID_INT = 5;
    private static final BigDecimal INVALID_BIG_DECIMAL_NEGATIVE = BigDecimal.valueOf(-1);

    @Test
    @DisplayName("When valid BigDecimal is provided, then Hours object is created successfully")
    void whenValidBigDecimalProvided_thenHoursIsCreatedSuccessfully() {
        assertDoesNotThrow(() -> {
            new Hours(VALID_BIG_DECIMAL);
        });
    }

    @Test
    @DisplayName("When valid String is provided, then Hours object is created successfully")
    void whenValidStringProvided_thenHoursIsCreatedSuccessfully() {
        assertDoesNotThrow(() -> {
            new Hours(VALID_STRING);
        });
    }

    @Test
    @DisplayName("When valid int is provided, then Hours object is created successfully")
    void whenValidIntProvided_thenHoursIsCreatedSuccessfully() {
        assertDoesNotThrow(() -> {
            new Hours(VALID_INT);
        });
    }

    @Test
    @DisplayName("When negative value is provided, then IllegalArgumentException is thrown")
    void whenNegativeValueProvided_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hours(INVALID_BIG_DECIMAL_NEGATIVE);
        });
    }

    @Test
    @DisplayName("When two Hours objects are compared, then the result is correct")
    void whenHoursAreCompared_thenCorrectResult() {
        Hours fiveHours = new Hours(VALID_BIG_DECIMAL);
        Hours threeHours = new Hours(BigDecimal.valueOf(3));

        assertTrue(fiveHours.isGreaterThanOrEqual(threeHours));
        assertFalse(threeHours.isGreaterThanOrEqual(fiveHours));
    }

    @Test
    @DisplayName("When Hours objects are added, then the result is correct")
    void whenHoursAreAdded_thenCorrectResult() {
        Hours fiveHours = new Hours(VALID_BIG_DECIMAL);
        Hours twoHours = new Hours(BigDecimal.valueOf(2));

        Hours result = fiveHours.add(twoHours);
        assertEquals(new Hours(BigDecimal.valueOf(7)), result);
    }

    @Test
    @DisplayName("When Hours are multiplied, then the result is correct")
    void whenHoursAreMultiplied_thenCorrectResult() {
        Hours fiveHours = new Hours(VALID_BIG_DECIMAL);

        Hours result = fiveHours.multiply(2);
        assertEquals(new Hours(BigDecimal.valueOf(10)), result);
    }

    @Test
    @DisplayName("When Hours are converted to BigDecimal, then the correct value is returned")
    void whenHoursAreConvertedToBigDecimal_thenCorrectValueReturned() {
        Hours fiveHours = new Hours(VALID_BIG_DECIMAL);

        assertEquals(VALID_BIG_DECIMAL, fiveHours.asBigDecimal());
    }

    @Test
    @DisplayName("When Hours are converted to String, then the correct value is returned")
    void whenHoursAreConvertedToString_thenCorrectValueReturned() {
        Hours fiveHours = new Hours(VALID_BIG_DECIMAL);

        assertEquals("5", fiveHours.asString());
    }

    @Test
    @DisplayName("When two Hours objects are compared for equality, then the result is correct")
    void whenHoursAreComparedForEquality_thenCorrectResult() {
        Hours fiveHours1 = new Hours(VALID_BIG_DECIMAL);
        Hours fiveHours2 = new Hours(VALID_BIG_DECIMAL);

        assertEquals(fiveHours1, fiveHours2);
        assertEquals(fiveHours1.hashCode(), fiveHours2.hashCode());
    }
}
