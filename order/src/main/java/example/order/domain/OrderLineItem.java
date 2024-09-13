package example.order.domain;

import example.common.domain.IdentifiedValueObject;
import example.common.domain.Hours;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode(callSuper = false)
public class OrderLineItem extends IdentifiedValueObject {
    final int MINIMUM_QUANTITY = 1;

    private int quantity;
    private long menuItemId;
    private String name;
    private Hours price;

    public OrderLineItem(long menuItemId, String name, Hours price, int quantity) {
        setMenuItemId(menuItemId);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public Hours deltaForChangedQuantity(int newQuantity) {
        return price.multiply(newQuantity - quantity);
    }

    private void setQuantity(int quantity) {
        assertValueIsGreaterThan( BigDecimal.valueOf(quantity), BigDecimal.valueOf(MINIMUM_QUANTITY),
                        "Quantity must be greater than " + (MINIMUM_QUANTITY-1));
        this.quantity = quantity;
    }

    private void setMenuItemId(long menuItemId) {
        assertArgumentNotEmpty(menuItemId, "Menu id cannot be empty");
        this.menuItemId = menuItemId;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    private void setPrice(Hours price) {
        assertArgumentNotEmpty(price, "Price cannot be empty");
        assertValueIsGreaterThan(price.asBigDecimal(), BigDecimal.valueOf(MINIMUM_QUANTITY),
                "Price must be greater than " + (MINIMUM_QUANTITY-1));
        this.price = price;
    }

    public int quantity() {
        return quantity;
    }

    public long menuItemId() {
        return menuItemId;
    }

    public String name() {
        return name;
    }

    public Hours price() {
        return price;
    }

    public Hours total() {
        return price.multiply(quantity);
    }
}
