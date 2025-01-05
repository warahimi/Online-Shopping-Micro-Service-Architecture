package com.cwc.inventory_service.service;

import com.cwc.inventory_service.model.Inventory;
import com.cwc.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }
    @Override
    public void addInventory(String skuCode, Integer quantity) {

    }

    @Override
    public void removeInventory(String skuCode, Integer quantity) {

    }

    @Override
    public Integer getInventory(String skuCode) {
        //return inventoryRepository.countBySkuCode(skuCode);
        return inventoryRepository.findBySkuCode(skuCode).get().getQuantity();
    }
    public boolean isInventoryAvailableList(List<String> skuCodes)
    {
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes);
        return inventories.stream().allMatch(inventory -> inventory.getQuantity() > 0);
    }
    @Override
    public void updateInventory(String skuCode, Integer quantity) {

    }

    @Override
    public void deleteInventory(String skuCode) {

    }

    @Override
    public void deleteAllInventory() {

    }

    @Override
    public boolean isInventoryAvailable(String skuCode, Integer quantity) {
        Optional<Inventory> bySkuCode = inventoryRepository.findBySkuCode(skuCode);
        if(bySkuCode.isPresent())
        {
            return bySkuCode.get().getQuantity() >= quantity;
        }
        return false;
    }

    @Override
    public boolean isInventoryAvailable(String skuCode) {
        Optional<Inventory> bySkuCode = inventoryRepository.findBySkuCode(skuCode);
        return bySkuCode.isPresent() && bySkuCode.get().getQuantity() > 0;
    }

    @Override
    public int getAvailableInventory(String skuCode) {
        return 0;
    }
}
