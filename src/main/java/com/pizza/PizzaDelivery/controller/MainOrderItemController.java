package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.entity.dto.MainOrderItemDto;
import com.pizza.PizzaDelivery.entity.request.MainOrderItemRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.MainOrderItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main-order")
public class MainOrderItemController {
    private final MainOrderItemService mainOrderItemService;
    private final MapperForDto mapperForDto;

    @Autowired
    public MainOrderItemController(MainOrderItemService mainOrderItemService, MapperForDto mapperForDto) {
        this.mainOrderItemService = mainOrderItemService;
        this.mapperForDto = mapperForDto;
    }

    @PostMapping("/create")
    public ResponseEntity<MainOrderItemDto> create(@Valid @RequestBody MainOrderItemRequest mainOrderItemRequest) {
        return new ResponseEntity<>(mapperForDto.mainOrderItemToDto(mainOrderItemService
                .createMainOrderItem(mainOrderItemRequest)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MainOrderItemDto> update(@RequestParam String id,
                                                   @RequestParam(required = false) String descr,
                                                   @RequestParam(required = false) String productId,
                                                   @RequestParam(required = false) Integer quantity) {
        return new ResponseEntity<>(mapperForDto.mainOrderItemToDto(mainOrderItemService
                .updateMainOrderItem(id, descr, productId, quantity)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteMainOrderItemById(@PathVariable String id) {
        return new ResponseEntity<>(mainOrderItemService.deleteMainOrderItemById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MainOrderItemDto>> findAllMainOrderByUsers() {
        return new ResponseEntity<>(mainOrderItemService.findAllMainOrderByUsers()
                .stream().map(mapperForDto::mainOrderItemToDto).toList(), HttpStatus.FOUND);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MainOrderItemDto>> findAllMainOrderItemsByUserAndFilter(@RequestParam(required = false) Double price,
                                                                                       @RequestParam(required = false) String before,
                                                                                       @RequestParam(required = false) String after,
                                                                                       @RequestParam(required = false) String descr,
                                                                                       @RequestParam(required = false) String id,
                                                                                       @RequestParam(required = false) String productId) {
        return new ResponseEntity<>(mainOrderItemService.findAllMainOrderItemsByUserAndFilter(price, before, after, descr, id, productId)
                .stream().map(mapperForDto::mainOrderItemToDto).toList(), HttpStatus.FOUND);
    }

}
