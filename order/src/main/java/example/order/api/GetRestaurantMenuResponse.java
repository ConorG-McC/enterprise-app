package example.order.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter //Needed for JSON
@Setter
@NoArgsConstructor
public class GetRestaurantMenuResponse {
    private String restaurantId;
    private String name;
    private List<BaseMenuItemValueObject> menuItems;
}
