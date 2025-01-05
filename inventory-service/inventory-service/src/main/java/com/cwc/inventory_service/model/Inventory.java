package com.cwc.inventory_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_inventory")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity; // Quantity in stock for the product with given skuCode

    public Inventory(String skuCode, int quantity) {
        this.skuCode = skuCode;
        this.quantity = quantity;
    }
}
