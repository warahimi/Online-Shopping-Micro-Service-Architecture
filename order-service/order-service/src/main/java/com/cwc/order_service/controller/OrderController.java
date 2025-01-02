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

}
