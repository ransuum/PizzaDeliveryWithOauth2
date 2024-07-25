package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.AdditionalOrderItem;
import com.pizza.PizzaDelivery.entity.dto.AdditionalItemDto;
import com.pizza.PizzaDelivery.entity.request.AdditionalOrderItemRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.AdditionalOrderItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/additional-item")
public class AdditionalOrderItemController {

    private final AdditionalOrderItemService additionalOrderItemService;
    private final MapperForDto mapper;

    @Autowired
    public AdditionalOrderItemController(AdditionalOrderItemService additionalOrderItemService, MapperForDto mapper) {
        this.additionalOrderItemService = additionalOrderItemService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ResponseEntity<AdditionalItemDto> createAdditionalOrderItem(@Valid @RequestBody AdditionalOrderItemRequest additionalOrderItemRequest) {
        return new ResponseEntity<>(mapper.additionalItemToDto(additionalOrderItemService
                .createAdditionalOrderItem(additionalOrderItemRequest)), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalItemDto> updateAdditionalOrderItem(@PathVariable String id,
                                                         @RequestParam(required = false) String productId,
                                                         @RequestParam(required = false) Integer quantity) {
        return new ResponseEntity<>(mapper.additionalItemToDto(additionalOrderItemService
                .updateAdditionalOrderItem(id, productId, quantity)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAdditionalOrderItem(@PathVariable String id) {
        return new ResponseEntity<>(additionalOrderItemService.deleteAdditionalOrderItem(id), HttpStatus.OK);
    }
}

