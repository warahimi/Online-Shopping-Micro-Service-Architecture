package com.cwc.order_service.service;

import com.cwc.order_service.dto.OrderRequest;
import com.cwc.order_service.dto.OrderResponse;
import com.cwc.order_service.model.Order;
import com.cwc.order_service.repository.OrderRepository;
import com.cwc.order_service.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        Order order = AppUtil.convertToOrder(orderRequest);
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = AppUtil.convertToOrderResponse(savedOrder);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        return null;
    }

    @Override
    public OrderResponse deleteOrderById(Long orderId) {
        return null;
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
    public void deleteAllOrders() {

    }
}
