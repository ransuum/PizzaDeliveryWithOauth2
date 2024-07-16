package com.pizza.PizzaDelivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String ex) {
        super(ex);
    }
}
