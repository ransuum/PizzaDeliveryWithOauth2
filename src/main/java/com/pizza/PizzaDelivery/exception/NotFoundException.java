package com.pizza.PizzaDelivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotFoundException extends AuthenticationException {
    public NotFoundException(String ex) {
        super(ex);
    }
}
