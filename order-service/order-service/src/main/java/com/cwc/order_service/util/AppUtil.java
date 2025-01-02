package com.cwc.order_service.util;

import com.cwc.order_service.dto.OrderLineItemsRequest;
import com.cwc.order_service.dto.OrderLineItemsResponse;
import com.cwc.order_service.dto.OrderRequest;
import com.cwc.order_service.dto.OrderResponse;
import com.cwc.order_service.model.Order;
import com.cwc.order_service.model.OrderLineItems;

import java.util.UUID;

public class AppUtil {
    public static Order convertToOrder(OrderRequest orderRequest) {
        String orderNumber = UUID.randomUUID().toString();
        return Order.builder()
                .orderNumber(orderNumber)
                .orderLineItems(orderRequest.getOrderLineItemsRequest()
                        .stream()
                        .map(orderLineItemsRequest -> convertToOrderLineItems(orderLineItemsRequest, orderNumber))
                        .toList())
                .build();
    }
    public static OrderLineItems convertToOrderLineItems(OrderLineItemsRequest orderLineItemsRequest, String orderNumber) {
        return OrderLineItems.builder()
                .orderNumber(orderNumber)
                .skuCode(orderLineItemsRequest.getSkuCode())
                .price(orderLineItemsRequest.getPrice())
                .quantity(orderLineItemsRequest.getQuantity())
                .build();
    }

    public static OrderResponse convertToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderNumber(order.getOrderNumber())
                .orderLineItems(order.getOrderLineItems().stream()
                        .map(orderLineItems -> convertToOrderLineItemsResponse(orderLineItems, order.getOrderNumber()))
                        .toList())
                .build();
    }
    private static OrderLineItemsResponse convertToOrderLineItemsResponse(OrderLineItems orderLineItems, String orderNumber) {
        return OrderLineItemsResponse.builder()
                .id(orderLineItems.getId())
                .orderNumber(orderNumber)
                .skuCode(orderLineItems.getSkuCode())
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .build();
    }
}
