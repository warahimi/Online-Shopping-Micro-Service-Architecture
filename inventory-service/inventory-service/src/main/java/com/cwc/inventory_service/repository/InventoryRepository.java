package com.cwc.inventory_service.repository;

import com.cwc.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
    void deleteBySkuCode(String skuCode);
    void deleteAllBySkuCode(String skuCode);
    void deleteAll();
    @Transactional(readOnly = true)
    boolean existsBySkuCode(String skuCode);
    int countBySkuCode(String skuCode);
    void deleteAllBySkuCodeAndQuantity(String skuCode, Integer quantity);

    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
