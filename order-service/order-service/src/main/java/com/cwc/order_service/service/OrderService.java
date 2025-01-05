package com.cwc.order_service.service;

import com.cwc.order_service.dto.OrderRequest;
import com.cwc.order_service.dto.OrderResponse;
import com.cwc.order_service.model.Order;
import com.cwc.order_service.repository.OrderRepository;
import com.cwc.order_service.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;


    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        // Collect the list of sku codes from the order request
        List<String> skuCodes = orderRequest.getOrderLineItemsRequest()
                .stream()
                .map(orderLineItemsRequest -> orderLineItemsRequest.getSkuCode())
                .toList();

        /* Call the inventory service to check if the items are available before placing the order */
        log.info("Checking inventory for availability of items");
        log.info("skuCodes: {}", skuCodes);

        // Convert skuCodes list to a comma-separated string
        String skuCodesQueryParam = String.join(",", skuCodes);

        Boolean available = webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("INVENTORY-SERVICE")
                        .port(8083)
                        .path("/api/v1/inventory/list")
                        .queryParam("skuCodes", skuCodesQueryParam)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (available != null && available) {
            Order order = AppUtil.convertToOrder(orderRequest);
            Order savedOrder = orderRepository.save(order);
            OrderResponse orderResponse = AppUtil.convertToOrderResponse(savedOrder);
            return orderResponse;
        } else {
            log.error("Items not available in inventory");
            throw new RuntimeException("Items not available in inventory");
        }
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty())
        {
            log.error("Order with id {} not found", orderId);
            return null;
        }
        return AppUtil.convertToOrderResponse(order.get());
    }
    @Override
    public OrderResponse getOrderByOrderNumber(String orderNumber)
    {
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        if(order.isEmpty())
        {
            log.error("Order with order number {} not found", orderNumber);
            return null;
        }
        return AppUtil.convertToOrderResponse(order.get());
    }


    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty())
        {
            log.error("Order with id {} not found", orderId);
            return null;
        }
        Order updatedOrder = order.get();
        updatedOrder.setOrderLineItems(orderRequest.getOrderLineItemsRequest()
                .stream()
                .map(orderLineItemsRequest -> AppUtil.
                        convertToOrderLineItems(orderLineItemsRequest, updatedOrder.getOrderNumber()))
                .toList());

        return AppUtil.convertToOrderResponse(orderRepository.save(updatedOrder));
    }

    @Override
    public OrderResponse deleteOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty())
        {
            log.error("Order with id {} not found", orderId);
            return null;
        }
        orderRepository.deleteById(orderId);
        return AppUtil.convertToOrderResponse(order.get());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            return List.of();
        }

        return orders.stream().map(AppUtil::convertToOrderResponse).toList();
    }

    @Override
    public String deleteAllOrders() {
        if(orderRepository.findAll().isEmpty())
        {
            log.error("No orders found");
            return "No orders found";
        }
        //orderRepository.deleteAll();
        return "All orders deleted";
    }
}
