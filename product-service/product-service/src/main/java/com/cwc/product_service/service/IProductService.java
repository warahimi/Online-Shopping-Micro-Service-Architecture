package com.cwc.product_service.service;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.exception.ProductNotFoundException;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(String id, ProductRequest productRequest) throws ProductNotFoundException;
    List<ProductResponse> getAllProducts() throws ProductNotFoundException;
    ProductResponse getProductById(String id) throws ProductNotFoundException;
    List<ProductResponse> getProductsByName(String name) throws ProductNotFoundException;
    List<ProductResponse> getProductsByPrice(double price) throws ProductNotFoundException;
    List<ProductResponse> getProductsByPriceRange(double min, double max) throws ProductNotFoundException;
    List<ProductResponse> getProductsByPriceRange(double min, double max, String name) throws ProductNotFoundException;
    List<ProductResponse> findByNameContainingIgnoreCase(String str) throws ProductNotFoundException;
    ProductResponse deleteProduct(String id) throws ProductNotFoundException;
    void deleteAllProducts() throws ProductNotFoundException;
}
