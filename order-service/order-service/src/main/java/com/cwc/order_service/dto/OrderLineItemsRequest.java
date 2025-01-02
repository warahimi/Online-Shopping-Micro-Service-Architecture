package com.cwc.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderLineItemsRequest {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
