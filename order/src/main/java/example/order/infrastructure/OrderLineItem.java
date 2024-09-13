package example.order.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.order.api.BaseOrderLineItemValueObject;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="order_item")//Needed for custom queries
@Table(name="order_item")
@ToString
@Getter
@Setter
@NoArgsConstructor //needed for the convertor
@AllArgsConstructor //needed for the convertor
//interface enables GetOrderItemsResponse to avoid coupling to interface layer via OrderLineItem
public class OrderLineItem implements BaseOrderLineItemValueObject {
    @Id
    @SequenceGenerator(name= "order_item_sequence",
            sequenceName = "order_item_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.IDENTITY,
            generator="order_item_sequence")
    @Column(name="order_item_id")
    private long id;

    @JsonIgnore //not displayed in JSONing menu items
    @Column(name="order_item_menu_item_id")
    private long menuItemId;

    @Column(name="order_item_menu_name")
    private String name;

    @Column(name="order_item_quantity")
    private int quantity;

    @Column(name="order_item_price")
    private BigDecimal price;

    @JsonIgnore //not displayed in JSONing menu items
    @Column(name="order_id")
    private String order_id;

    ////totalOfOrder is called by Orders to provide a total of all order lines
    public double totalOfOrder(){ //Name will not get picked up by JSON (no get prefix) if OrderLineItem is used by Response
        return quantity * price.doubleValue();
    }
}
