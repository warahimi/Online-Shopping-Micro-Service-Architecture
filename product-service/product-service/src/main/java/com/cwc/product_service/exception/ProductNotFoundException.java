package com.cwc.product_service.exception;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(String string) {
        super(string);
    }
}
