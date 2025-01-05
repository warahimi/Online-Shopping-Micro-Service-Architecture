package com.cwc.inventory_service.service;

import com.cwc.inventory_service.model.Inventory;

import java.util.List;

public interface IInventoryService {
    List<Inventory> getAllInventory();
    void addInventory(String skuCode, Integer quantity);
    void removeInventory(String skuCode, Integer quantity);
    Integer getInventory(String skuCode);
    void updateInventory(String skuCode, Integer quantity);
    void deleteInventory(String skuCode);
    void deleteAllInventory();
    boolean isInventoryAvailable(String skuCode, Integer quantity);
    boolean isInventoryAvailable(String skuCode);
    int getAvailableInventory(String skuCode);
}
