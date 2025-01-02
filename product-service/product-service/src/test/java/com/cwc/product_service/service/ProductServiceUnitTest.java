package com.cwc.product_service.service;

import com.cwc.product_service.dto.ProductRequest;
import com.cwc.product_service.dto.ProductResponse;
import com.cwc.product_service.exception.ProductNotFoundException;
import com.cwc.product_service.model.Product;
import com.cwc.product_service.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .id("1")
                .name("test")
                .price(100.0)
                .description("this is a test product")
                .build();
        productRequest = ProductRequest.builder()
                .name("test")
                .price(100.0)
                .description("this is a test product")
                .build();
        productResponse = ProductResponse.builder()
                .id("1")
                .name("test")
                .price(100.0)
                .description("this is a test product")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse productResponse = productService.createProduct(productRequest);
        Assertions.assertNotNull(productResponse);

        Assertions.assertEquals(productResponse.getId(), product.getId(),"ProductResposne id must match product id");
        Assertions.assertEquals(productResponse.getName(), product.getName(),"ProductResposne name must match product name");
        Assertions.assertEquals(productResponse.getPrice(), product.getPrice(),"ProductResposne price must match product price");
        Assertions.assertEquals(productResponse.getDescription(), product.getDescription(),"ProductResposne description must match product description");
        Assertions.assertEquals(productResponse.getName(), productRequest.getName(),"ProductResposne name must match product name");
        Assertions.assertEquals(productResponse.getPrice(), productRequest.getPrice(),"ProductResposne price must match product price");
        Assertions.assertEquals(productResponse.getDescription(), productRequest.getDescription(),"ProductResposne description must match product description");

        Assertions.assertAll(
                () -> Assertions.assertEquals(productResponse.getId(), product.getId(),"ProductResposne id must match product id"),
                () -> Assertions.assertEquals(productResponse.getName(), product.getName(),"ProductResposne name must match product name"),
                () -> Assertions.assertEquals(productResponse.getPrice(), product.getPrice(),"ProductResposne price must match product price"),
                () -> Assertions.assertEquals(productResponse.getId(), product.getId(),"ProductResposne id must match product id"),
                () ->Assertions.assertEquals(productResponse.getName(), product.getName(),"ProductResposne name must match product name"),
                ()->Assertions.assertEquals(productResponse.getPrice(), product.getPrice(),"ProductResposne price must match product price"),
                ()->Assertions.assertEquals(productResponse.getDescription(), product.getDescription(),"ProductResposne description must match product description"),
                ()->Assertions.assertEquals(productResponse.getName(), productRequest.getName(),"ProductResposne name must match product name"),
                ()->Assertions.assertEquals(productResponse.getPrice(), productRequest.getPrice(),"ProductResposne price must match product price"),
                ()->Assertions.assertEquals(productResponse.getDescription(), productRequest.getDescription(),"ProductResposne description must match product description")
        );

        verify(productRepository,times(1)).save(any(Product.class));

    }

    @Test
    void testUpdateProduct() throws ProductNotFoundException {
        when(productRepository.existsById("1")).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse productResponse = productService.updateProduct("1", productRequest);
        assertThat(productResponse).isNotNull();
        assertThat(productResponse.getId()).isEqualTo(product.getId());
        assertThat(productResponse.getName()).isEqualTo(product.getName());
        assertThat(productResponse.getPrice()).isEqualTo(product.getPrice());
        assertThat(productResponse.getDescription()).isEqualTo(product.getDescription());
        assertThat(productResponse.getName()).isEqualTo(productRequest.getName());
        assertThat(productResponse.getPrice()).isEqualTo(productRequest.getPrice());
        assertThat(productResponse.getDescription()).isEqualTo(productRequest.getDescription());

        verify(productRepository, times(1)).existsById("1");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void tetGetAllProducts() throws ProductNotFoundException {
        when(productRepository.findAll()).thenReturn(java.util.List.of(product));
        Assertions.assertDoesNotThrow(() -> productService.getAllProducts());
        assertThatCode(() -> productService.getAllProducts()).doesNotThrowAnyException();
        //Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getAllProducts());
        //assertThatThrownBy(() -> productService.getAllProducts()).isInstanceOf(ProductNotFoundException.class);
        assertThat(productService.getAllProducts().size()).isEqualTo(1);
        assertThat(productService.getAllProducts().size()).isEqualTo(1);
        assertThat(productService.getAllProducts().get(0).getId()).isEqualTo(product.getId());
        assertThat(productService.getAllProducts().get(0).getName()).isEqualTo(product.getName());
        assertThat(productService.getAllProducts().get(0).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getAllProducts().get(0).getDescription()).isEqualTo(product.getDescription());
        verify(productRepository, times(8)).findAll();
    }
    @Test
    void testGetAllProductShouldThrowProductNotFoundException() {
        when(productRepository.findAll()).thenReturn(java.util.List.of());
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getAllProducts());
        assertThatThrownBy(() -> productService.getAllProducts()).isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, times(2)).findAll();
    }

    @Test
    void getProductById() throws ProductNotFoundException {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        ProductResponse productResponse = productService.getProductById("1");

        assertNotNull(productResponse);
        assertEquals(productResponse.getDescription(), product.getDescription(),"Description does not match");
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testGetProductsByName() throws ProductNotFoundException {
        when(productRepository.findByName("test")).thenReturn(java.util.List.of(product));
        assertThat(productService.getProductsByName("test").size()).isEqualTo(1);
        assertThat(productService.getProductsByName("test").get(0).getId()).isEqualTo(product.getId());
        assertThat(productService.getProductsByName("test").get(0).getName()).isEqualTo(product.getName());
        assertThat(productService.getProductsByName("test").get(0).getPrice()).isEqualTo(product.getPrice());
        assertThat(productService.getProductsByName("test").get(0).getDescription()).isEqualTo(product.getDescription());
        verify(productRepository, times(5)).findByName("test");
    }

    @Test
    void testGetProductsByPrice() throws ProductNotFoundException {
        when(productRepository.findByPrice(100.0)).thenReturn(java.util.List.of(product));
        assertThat(productService.getProductsByPrice(100.0).size()).isEqualTo(1);
        assertThat(productService.getProductsByPrice(100.0).get(0).getId()).isEqualTo(product.getId());
        assertThat(productService.getProductsByPrice(100.0).get(0).getName()).isEqualTo(product.getName());
    }

    @Test
    void getProductsByPriceRange() {
    }

    @Test
    void testGetProductsByPriceRange() {
    }

    @Test
    void findByNameContainingIgnoreCase() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void deleteAllProducts() {
    }
}