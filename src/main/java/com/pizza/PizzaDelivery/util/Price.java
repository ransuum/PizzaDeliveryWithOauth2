package com.pizza.PizzaDelivery.util;

import com.pizza.PizzaDelivery.enums.AdditionalItem;
import com.pizza.PizzaDelivery.enums.CategoryForPizza;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Price {

    public Double priceOnPizza(CategoryForPizza pizzaName) {
        return switch (pizzaName) {
            case BAVARIAN -> 25.5;
            case SICILIAN -> 15.0;
            case CARBONARA -> 5.0;
            case NEW_YORK_SLICE -> 125.0;
            default -> throw new NotFoundException("Pizza name not found");
        };
    }

    public Double priceOnAdditional(AdditionalItem additionalItem){
        return switch (additionalItem){
            case FANTA -> 2d;
            case FRIES -> 7d;
            case SHAKE -> 5d;
            case SPRITE -> 2d;
            case KETCHUP -> 2d;
            default -> throw new NotFoundException("Additional item not found");
        };
    }
}
