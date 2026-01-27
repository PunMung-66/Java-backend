package com.punnawat.backend.expections;

import org.springframework.http.HttpStatus;

public class ProductException extends BaseException {
    public ProductException(String code, HttpStatus status) {
        super("product." + code, status);
    }

    public static ProductException productNotFound() {
        return new ProductException("notfound", HttpStatus.NOT_FOUND);
    }

    public static ProductException idNull() {
        return new ProductException("id.null", HttpStatus.EXPECTATION_FAILED);
    }
}
