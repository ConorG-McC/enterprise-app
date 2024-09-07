package example.order.application;

import example.order.api.GetOrderItemsResponse;
import example.order.api.GetOrderSummaryResponse;
import example.order.api.events.OrderState;
import example.order.infrastructure.OrderRepository;
import example.order.infrastructure.Orders;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderQueryHandler {
    private OrderRepository orderRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetOrderSummaryResponse> getOrderSummary(String orderId) {
        Optional<Orders> order = orderRepository.findById(orderId);
        //Take the order and map via makeGetOrderSummaryResponse if optional.isPresent() else not found
        return order.flatMap(order1 -> Optional.of(makeGetOrderSummaryResponse(order1)));
    }

    public Optional<GetOrderItemsResponse> getOrderItems(String orderId) {
        Optional<Orders> order = orderRepository.findById(orderId);
        return order.flatMap(order1 -> Optional.of(makeGetOrderItemsResponse(order1)));
    }

    //No mapper used as we want to convert before assigning to response to avoid coupling to infrastructure
    private GetOrderSummaryResponse makeGetOrderSummaryResponse(Orders order) {
        return new GetOrderSummaryResponse(order.getId(),
                                            OrderState.values()[order.getOrder_state()].name(), //
                                            order.getTotalOfOrder());//Avoid JSON conversion error
    }

    private GetOrderItemsResponse makeGetOrderItemsResponse(Orders order) {
        GetOrderItemsResponse response = modelMapper.map(order, GetOrderItemsResponse.class);
        response.setDeliverTo(); //Create concatenated addressing following map
        return response;
    }
}
