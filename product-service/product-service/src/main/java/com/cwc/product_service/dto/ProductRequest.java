package com.cwc.product_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 200, message = "Description must be between 5 and 200 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price must be positive or zero")
    @Max(value = 100000, message = "Price must be less than 100000")
    private Double price;
}