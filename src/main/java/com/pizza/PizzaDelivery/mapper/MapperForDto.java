package com.pizza.PizzaDelivery.mapper;

import com.pizza.PizzaDelivery.entity.*;
import com.pizza.PizzaDelivery.entity.dto.*;
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

    public OrderDto orderToDto(Orders orders){
        return mapper.map(orders, OrderDto.class);
    }

    public ProductDto productToDto(Product product){
        return mapper.map(product, ProductDto.class);
    }

    public MainOrderItemDto mainOrderItemToDto(MainOrderItem mainOrderItem){
        return mapper.map(mainOrderItem, MainOrderItemDto.class);
    }

    public AdditionalItemDto additionalItemToDto(AdditionalOrderItem additionalItem){
        return mapper.map(additionalItem, AdditionalItemDto.class);
    }

}
