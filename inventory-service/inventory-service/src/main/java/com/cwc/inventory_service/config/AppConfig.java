package com.cwc.inventory_service.config;

import com.cwc.inventory_service.model.Inventory;
import com.cwc.inventory_service.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration@Slf4j
public class AppConfig {
    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            log.info("Loading sample inventory data data into database");
            if(inventoryRepository.count() == 0)
            {
                inventoryRepository.saveAll(List.of(
                        new Inventory("Iphone13", 100),
                        new Inventory("Laptop", 0),
                        new Inventory("SKU003", 300),
                        new Inventory("SKU004", 400),
                        new Inventory("SKU005", 500)
                ));
            }

        };
    }
}
