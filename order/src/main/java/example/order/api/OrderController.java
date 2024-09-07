package example.order.api;

import example.order.application.OrderApplicationService;
import example.order.application.OrderQueryHandler;
import example.order.application.OrderingDomainException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    private OrderQueryHandler orderQueryHandler;
    private OrderApplicationService orderApplicationService;

    //e.g. http://localhost:8900/orders/summary/ord1
    @GetMapping(path = "/summary/{orderId}")
    public ResponseEntity<GetOrderSummaryResponse> getOrderSummary(@PathVariable String orderId) {
        return orderQueryHandler.getOrderSummary(orderId).map(
                o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //e.g. http://localhost:8900/orders/ord1
    @RequestMapping(path = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<GetOrderItemsResponse> getOrderItems(@PathVariable String orderId) {
        return orderQueryHandler.getOrderItems(orderId).map(
                o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /** e.g. http://localhost:8900/orders/newOrder
    {
        "consumerId": "1234",
            "restaurantId":"r1",
            "paymentMethodId":"visa",
            "deliveryDateTime":"2023-10-27T21:00",
            "deliveryAddress":{
        "houseNameNumber":"10",
                "street":"High Street",
                "town":"Stoke",
                "postalCode":"ST1 12G"
    },
        "orderLineItems":[
        {"menuItemId":"1",
                "name":"basmati rice",
                "price":2.5,
                "quantity":2},
        {"menuItemId":"2",
                "name":"chicken korma",
                "price":9.5,
                "quantity":2}
    ]
    }**/
    @PostMapping("/newOrder")
    public HttpStatus addOrder(@RequestBody AddNewOrderCommand command){
        try {
            orderApplicationService.addNewOrder(command);
            return HttpStatus.CREATED;
        }
        catch(OrderingDomainException e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/{orderId}/cancel")
    public HttpStatus cancelOrder(@PathVariable String orderId){
        try {
            orderApplicationService.cancelOrder(orderId);
            return HttpStatus.OK;
        }
        catch(OrderingDomainException e){
            return HttpStatus.BAD_REQUEST;
        }
    }
}