package com.cwc.order_service.service;

import com.cwc.order_service.dto.OrderLineItemsRequest;
import com.cwc.order_service.dto.OrderLineItemsResponse;
import com.cwc.order_service.dto.OrderRequest;
import com.cwc.order_service.dto.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse saveOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long orderId);
    OrderResponse updateOrder(Long orderId, OrderRequest orderRequest);
    OrderResponse deleteOrderById(Long orderId);
//    void addLineItems(Long orderId, OrderLineItemsRequest orderLineItemsRequest);
//    void updateLineItems(Long orderId, Long lineItemId, OrderLineItemsRequest orderLineItemsRequest);
//    void deleteLineItems(Long orderId, Long lineItemId);
    List<OrderResponse> getAllOrders();
    //List<OrderLineItemsResponse> getLineItems(Long orderId);
    //OrderLineItemsResponse getLineItem(Long orderId, Long lineItemId);
    void deleteAllOrders();
}
