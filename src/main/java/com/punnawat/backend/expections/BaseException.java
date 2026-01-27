package com.punnawat.backend.expections;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends Exception {
    private final HttpStatus status;

    public BaseException(String code, HttpStatus status){
        super(code);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return this.status;
    };
}
