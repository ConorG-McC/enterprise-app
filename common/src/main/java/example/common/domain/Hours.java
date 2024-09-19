package example.common.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Hours extends ValueObject {

    public static Hours ZERO = new Hours(0);

    private BigDecimal amount;

    public Hours(BigDecimal amount) {
        assertArgumentNotEmpty(amount.toString(), "Hours cannot be empty");
        assertValueIsGreaterThan(amount, BigDecimal.ZERO, "Hours cannot be greater than zero");
        this.amount = amount;
    }

    public Hours(String s) {
        this.amount = new BigDecimal(s);
    }

    public Hours(int i) {
        this.amount = new BigDecimal(i);
    }

    //Shallow copy
    public Hours(Hours hours) {
        this(hours.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Hours hours = (Hours) o;
        return this.amount.equals(hours.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.format("amount %s", amount.toString());
    }

    public Hours add(Hours delta) {
        return new Hours(amount.add(delta.amount));
    }

    public boolean isGreaterThanOrEqual(Hours other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public BigDecimal asBigDecimal() {
        return amount;
    }

    public double asDouble() {
        return amount.doubleValue();
    }

    public String asString() {
        return amount.toPlainString();
    }

    public Hours multiply(int x) {
        return new Hours(amount.multiply(new BigDecimal(x)));
    }
}

