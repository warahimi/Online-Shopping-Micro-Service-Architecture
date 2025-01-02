package com.cwc.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequest {
    private List<OrderLineItemsRequest> orderLineItemsRequest;
}
