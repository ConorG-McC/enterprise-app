package example.assignment.api.events;

import example.common.domain.Hours;
import lombok.AllArgsConstructor;
import lombok.Setter;


//Version for events (based on domain OrderLineItem) so that reuse of this event class by another context
//is not coupled to the domain version
@AllArgsConstructor
@Setter
public class OrderLineItem {
    private int quantity;
    private long menuItemId;
    private String name;
    private Hours price;

    public Hours deltaForChangedQuantity(int newQuantity) {
        return price.multiply(newQuantity - quantity);
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
