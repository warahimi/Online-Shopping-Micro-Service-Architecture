package com.cwc.inventory_service.controller;

import com.cwc.inventory_service.model.Inventory;
import com.cwc.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory()
    {
        return inventoryService.getAllInventory();
    }
    @GetMapping("/{skuCode}")
    public boolean isInventoryAvailable(@PathVariable String skuCode)
    {
        return inventoryService.isInventoryAvailable(skuCode);
    }

    // http://localhost:8083/api/v1/inventory/list?skuCodes=Iphone13&skuCodes=Laptop&skuCodes=SKU003
    @GetMapping("/list")
    public boolean isInventoryAvailableList(@RequestParam List<String> skuCodes)
    {
        return inventoryService.isInventoryAvailableList(skuCodes);
    }
    @GetMapping("/count/{skuCode}")
    public Integer getInventory(@PathVariable String skuCode)
    {
        return inventoryService.getInventory(skuCode);
    }

    @GetMapping("/{skuCode}/{quantity}")
    public boolean isInventoryAvailable(@PathVariable String skuCode, @PathVariable Integer quantity)
    {
        return inventoryService.isInventoryAvailable(skuCode, quantity);
    }
}
