package com.cwc.product_service.service;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.exception.ProductNotFoundException;
import com.cwc.product_service.model.Product;
import com.cwc.product_service.repository.ProductRepository;
import com.cwc.product_service.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product savedProduct = productRepository.save(AppUtil.convertToProduct(productRequest));
        log.info("Product with id: " + savedProduct.getId() + " created");
        return AppUtil.convertToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) throws ProductNotFoundException {
        if(productRepository.existsById(id)) {
            log.info("Product with id: " + id + " updated");
            return AppUtil.convertToProductResponse(productRepository.save(AppUtil.convertToProduct(productRequest)));
        }
        else {
            log.error("Product with id: " + id + " not found");
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() throws ProductNotFoundException {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts.isEmpty()) {
            log.error("No products found");
            throw new ProductNotFoundException("No products found");
        }
        log.info("All products retrieved");
        return allProducts.stream().map(AppUtil::convertToProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(String id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
        {
            log.error("Product with id: " + id + " not found");
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }
        log.info("Product with id: " + id + " retrieved");
        return AppUtil.convertToProductResponse(product.get());
    }

    @Override
    public List<ProductResponse> getProductsByName(String name) throws ProductNotFoundException {
        List<Product> product = productRepository.findByName(name);
        if (product.isEmpty())
        {
            log.error("Products with name: " + name + " not found");
            throw new ProductNotFoundException("Products with name: " + name + " not found");
        }
        log.info("Products with name: " + name + " retrieved");
        return product.stream().map(AppUtil::convertToProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByPrice(double price) throws ProductNotFoundException {
        List<Product> products = productRepository.findByPrice(price);
        if(products.isEmpty())
        {
            log.error("Products with price: " + price + " not found");
            throw new ProductNotFoundException("Products with price: " + price + " not found");
        }
        log.info("Products with price: " + price + " retrieved");
        return products.stream().map(AppUtil::convertToProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByPriceRange(double min, double max) throws ProductNotFoundException {
        if(min >= max || min < 0 || max > 100000)
        {
            log.error("Minimum price must be less than maximum price");
            throw new ProductNotFoundException("Minimum price must be less than maximum price");
        }
        List<Product> products = productRepository.findByPriceBetween(min, max);
        if(products.isEmpty())
        {
            log.error("Products with price between: " + min + " and " + max + " not found");
            throw new ProductNotFoundException("Products with price between: " + min + " and " + max + " not found");
        }
        log.info("Products with price between: " + min + " and " + max + " retrieved");
        return products.stream().map(AppUtil::convertToProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByPriceRange(double min, double max, String name) throws ProductNotFoundException {
        if(min >= max || min < 0 || name.isEmpty() || name.isBlank() || max > 100000)
        {
            throw new ProductNotFoundException("Minimum price must be less than maximum price");
        }
        List<Product> products = productRepository.findByPriceBetween(min, max);
        if(products.isEmpty())
        {
            throw new ProductNotFoundException("Products with price between: " + min + " and " + max + " not found");
        }
        products = products.stream().filter(product -> product.getName().equals(name)).collect(Collectors.toList());
        if(products.isEmpty())
        {
            throw new ProductNotFoundException("Products with name: " + name + " not found");
        }
        return products.stream().map(AppUtil::convertToProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByNameContainingIgnoreCase(String str) throws ProductNotFoundException {
        List<ProductResponse> collect = productRepository.findAll()
                .stream()
                .filter(product -> product.getName().contains(str) || product.getDescription().contains(str))
                .map(AppUtil::convertToProductResponse).collect(Collectors.toList());
        if(collect.isEmpty())
        {
            log.error("Products with name containing: " + str + " not found");
            throw new ProductNotFoundException("Products with name containing: " + str + " not found");
        }
        log.info("Products with name containing: " + str + " retrieved");
        return collect;
    }

    @Override
    public ProductResponse deleteProduct(String id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
        {
            log.error("Product with id: " + id + " not found");
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }
        productRepository.deleteById(id);
        log.info("Product with id: " + id + " deleted");
        return AppUtil.convertToProductResponse(product.get());
    }

    @Override
    public void deleteAllProducts() throws ProductNotFoundException {
        if(productRepository.findAll().isEmpty())
        {
            log.error("No products found");
            throw new ProductNotFoundException("No products found");
        }
        productRepository.deleteAll();
        log.info("All products deleted");
    }


}
