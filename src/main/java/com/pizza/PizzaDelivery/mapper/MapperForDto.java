package com.pizza.PizzaDelivery.mapper;

import com.pizza.PizzaDelivery.entity.Users;
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


}
