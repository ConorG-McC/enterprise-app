package example.order.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor //For model mapper
public class GetRestaurantResponse {
    private String restaurantId;
    private String name;
}