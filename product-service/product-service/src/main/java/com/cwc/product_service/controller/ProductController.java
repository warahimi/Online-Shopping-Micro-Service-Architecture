package com.cwc.product_service.controller;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.exception.ProductNotFoundException;
import com.cwc.product_service.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @RequestMapping("/test")
    public ResponseEntity<ProductResponse> test()
    {
        ProductRequest productRequest = ProductRequest.builder()
                .name("test")
                .description("this is a test description ")
                .price(100.0)
                .build();
        ProductResponse product = productService.createProduct(productRequest);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id,
                                                         @RequestBody @Valid ProductRequest productRequest)
            throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(id, productRequest));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() throws ProductNotFoundException
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getProductById(id));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponse>> getProductsByName(@PathVariable String name) throws ProductNotFoundException
    {
         return ResponseEntity.status(HttpStatus.FOUND).body(productService.getProductsByName(name));
    }
    @GetMapping("/price/{price}")
    public ResponseEntity<List<ProductResponse>> getProductsByPrice(@PathVariable double price) throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getProductsByPrice(price));
    }
    @GetMapping("/price/{min}/{max}")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(@PathVariable double min, @PathVariable double max) throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getProductsByPriceRange(min, max));
    }
    @GetMapping("/price/{min}/{max}/{name}")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(double min, double max, String name) throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getProductsByPriceRange(min, max, name));
    }
    @GetMapping("/search/{str}")
    public ResponseEntity<List<ProductResponse>> findByNameContainingIgnoreCase(String str) throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findByNameContainingIgnoreCase(str));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable String id) throws ProductNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(id));
    }
    @DeleteMapping("/delete/all")
    public void deleteAllProducts() throws ProductNotFoundException {
        productService.deleteAllProducts();
    }
}
