package example.order.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter //Needed for JSON
@Setter
@EqualsAndHashCode
@NoArgsConstructor //Needed for ModelMapper
public class GetOrderItemsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime delivery_day_time;
    private List<BaseOrderLineItemValueObject> orderLineItems;

    @JsonIgnore
    private String delivery_address_house_name_number;
    @JsonIgnore
    private String delivery_address_street;
    @JsonIgnore
    private String delivery_address_town;
    @JsonIgnore
    private String delivery_address_postal_code;

    private String deliver_to; //Created in constructor to simplify JSON output for this class re delivery info
    public void setDeliverTo() {//called in the query handler
        this.deliver_to = String.format("%s, %s, %s, %s",
                delivery_address_house_name_number,
                delivery_address_street,
                delivery_address_town,
                delivery_address_postal_code);
    }
}
