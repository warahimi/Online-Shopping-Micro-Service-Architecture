package com.cwc.product_service.repository;

import com.cwc.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByName(String name);
    List<Product> findByPrice(double price);
    List<Product> findByPriceBetween(double min, double max);
}
