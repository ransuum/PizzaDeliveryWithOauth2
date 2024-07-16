package com.pizza.PizzaDelivery.mapper;

import com.pizza.PizzaDelivery.entity.Order;
import com.pizza.PizzaDelivery.entity.Product;
import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.dto.OrderDto;
import com.pizza.PizzaDelivery.entity.dto.ProductDto;
import com.pizza.PizzaDelivery.entity.dto.UsersDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperForDto {

    private final ModelMapper mapper;

    public UsersDto userToDto(Users users){
        return mapper.map(users, UsersDto.class);
    }

    public OrderDto orderToDto(Order order){
        return mapper.map(order, OrderDto.class);
    }

    public ProductDto productToDto(Product product){
        return mapper.map(product, ProductDto.class);
    }

}
