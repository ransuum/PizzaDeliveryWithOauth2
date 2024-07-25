package com.pizza.PizzaDelivery.util;

import com.pizza.PizzaDelivery.exception.FieldValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parse(String string){
        try {return LocalDateTime.parse(string,formatter);}
        catch (Exception ex){
            throw  new FieldValidationException("Incorrect local date format. Must be: yyyy-MM-dd HH:mm:ss");
        }
    }
}
