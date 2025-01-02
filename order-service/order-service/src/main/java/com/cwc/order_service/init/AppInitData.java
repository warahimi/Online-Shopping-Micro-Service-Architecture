package com.cwc.order_service.init;

import com.cwc.order_service.dto.OrderLineItemsRequest;
import com.cwc.order_service.dto.OrderRequest;
import com.cwc.order_service.repository.OrderRepository;
import com.cwc.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppInitData implements CommandLineRunner {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private OrderLineItemsRequest orderLineItemsRequest1;
    private OrderLineItemsRequest orderLineItemsRequest2;
    private OrderRequest orderRequest;
    @Override
    public void run(String... args) throws Exception {
        if(orderRepository.count() == 0){
            // Add some initial data
            // Order 1
            orderLineItemsRequest1 = OrderLineItemsRequest.builder()
                    .skuCode("Iphone16")
                    .price(BigDecimal.valueOf(999.99))
                    .quantity(2)
                    .build();
            orderLineItemsRequest2 = OrderLineItemsRequest.builder()
                    .skuCode("Laptop")
                    .price(BigDecimal.valueOf(1200.99))
                    .quantity(20)
                    .build();
            orderRequest = OrderRequest.builder()
                    .orderLineItemsRequest(List.of(orderLineItemsRequest1, orderLineItemsRequest2))
                    .build();
            orderService.saveOrder(orderRequest);

            // Order 2
            orderLineItemsRequest1 = OrderLineItemsRequest.builder()
                    .skuCode("Mouse")
                    .price(BigDecimal.valueOf(99.99))
                    .quantity(12)
                    .build();
            orderLineItemsRequest2 = OrderLineItemsRequest.builder()
                    .skuCode("Keyboard")
                    .price(BigDecimal.valueOf(120.99))
                    .quantity(120)
                    .build();
            orderRequest = OrderRequest.builder()
                    .orderLineItemsRequest(List.of(orderLineItemsRequest1, orderLineItemsRequest2))
                    .build();
            orderService.saveOrder(orderRequest);

        }
    }
}
