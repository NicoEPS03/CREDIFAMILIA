package com.prueba.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends Exception{

    private static final long serialVersionUIDLONG = 1L;

    public ModelNotFoundException(String message) {
        super(message);
    }

}