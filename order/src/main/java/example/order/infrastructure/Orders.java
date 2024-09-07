package example.order.infrastructure;

import example.order.api.BaseOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="orders")//Needed for custom queries
@Table(name="orders")
@ToString
@Getter
@Setter
public class Orders implements BaseOrder{
    @Id
    @Column(name="id")
    private String id;

    @Column(name="consumer_id")
    private String consumer_id;

    @Column(name="restaurant_id")
    private String restaurant_id;

    @Column(name="payment_method")
    private String payment_method;

    @Column(name="order_state")
    private int order_state;

    //You can make these embedded and map the fields
    @Column(name="delivery_day_time")
    private LocalDateTime delivery_day_time;

    @Column(name="delivery_address_house_name_number")
    private String delivery_address_house_name_number;

    @Column(name="delivery_address_street")
    private String delivery_address_street;

    @Column(name="delivery_address_town")
    private String delivery_address_town;

    @Column(name="delivery_address_postal_code")
    private String delivery_address_postal_code;

    @OneToMany(mappedBy = "order_id", cascade = {CascadeType.ALL})
    private List<OrderLineItem> orderLineItems;

    protected Orders() {}
    //Needed for the convertor
    protected Orders(String id,
                     String consumer_id,
                     String restaurant_id,
                     String payment_method,
                     int order_state,
                     LocalDateTime delivery_day_time,
                     String delivery_address_house_name_number,
                     String delivery_address_street,
                     String delivery_address_town,
                     String delivery_address_postal_code
                     ) {
        this.id = id;
        this.consumer_id = consumer_id;
        this.restaurant_id = restaurant_id;
        this.payment_method = payment_method;
        this.order_state = order_state;
        this.delivery_day_time = delivery_day_time;
        this.delivery_address_house_name_number = delivery_address_house_name_number;
        this.delivery_address_street = delivery_address_street;
        this.delivery_address_town = delivery_address_town;
        this.delivery_address_postal_code = delivery_address_postal_code;
        this.orderLineItems = new ArrayList<>(); //Initialise the empty list in order to allow orderLineItems to work
    }
    //Needed for the convertor
    public void addOrderItem(OrderLineItem orderLineItem) {
        orderLineItems.add(orderLineItem);
    }

    //used by OrderQueryHandler
    public double getTotalOfOrder(){
        return orderLineItems.stream()
                .mapToDouble(OrderLineItem::totalOfOrder)
                .sum();
    }

    //Factory method
    public static Orders orderOf(String id,
                                 String consumer_id,
                                 String restaurant_id,
                                 String payment_method,
                                 int order_state,
                                 LocalDateTime delivery_day_time,
                                 String delivery_address_house_name_number,
                                 String delivery_address_street,
                                 String delivery_address_town,
                                 String delivery_address_postal_code) {
        return new Orders(id,
                        consumer_id,
                        restaurant_id,
                        payment_method,
                        order_state,
                        delivery_day_time,
                        delivery_address_house_name_number,
                        delivery_address_street,
                        delivery_address_town,
                        delivery_address_postal_code);
    }
}