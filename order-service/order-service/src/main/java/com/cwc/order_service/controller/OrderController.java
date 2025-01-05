package com.cwc.order_service.controller;

import com.cwc.order_service.dto.OrderRequest;
import com.cwc.order_service.dto.OrderResponse;
import com.cwc.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(orderRequest));
    };
    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.FOUND).body(orderService.getAllOrders());
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(orderService.getOrderById(orderId));
    }
    @GetMapping("/orderNumber/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrderByOrderNumber(@PathVariable String orderNumber)
    {
        return  ResponseEntity.status(HttpStatus.FOUND).body(orderService.getOrderByOrderNumber(orderNumber));
    }
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(orderId, orderRequest));
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Order with id " + orderId + " deleted successfully");
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllOrders() {
        orderService.deleteAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body("All orders deleted successfully");
    }

}
