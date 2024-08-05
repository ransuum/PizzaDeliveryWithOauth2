package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.entity.dto.MainOrderItemDto;
import com.pizza.PizzaDelivery.entity.request.MainOrderItemRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.MainOrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main-order")
@Tag(name = "Order Management", description = "API for main order management")
public class MainOrderItemController {
    private final MainOrderItemService mainOrderItemService;
    private final MapperForDto mapperForDto;

    @Autowired
    public MainOrderItemController(MainOrderItemService mainOrderItemService, MapperForDto mapperForDto) {
        this.mainOrderItemService = mainOrderItemService;
        this.mapperForDto = mapperForDto;
    }

    @PostMapping("/create")
    @Operation(summary = "Create an order item", description = "Creates a new order item based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The order element was successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Product or order not found")
    })
    public ResponseEntity<MainOrderItemDto> create(@Valid @RequestBody MainOrderItemRequest mainOrderItemRequest) {
        return new ResponseEntity<>(mapperForDto.mainOrderItemToDto(mainOrderItemService
                .createMainOrderItem(mainOrderItemRequest)), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update an order item", description = "Updates an order item based on the provided data")
    public ResponseEntity<MainOrderItemDto> update(@RequestParam String id,
                                                   @RequestParam(required = false) String descr,
                                                   @RequestParam(required = false) String productId,
                                                   @RequestParam(required = false) Integer quantity) {
        return new ResponseEntity<>(mapperForDto.mainOrderItemToDto(mainOrderItemService
                .updateMainOrderItem(id, descr, productId, quantity)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order item", description = "Deletes an order item based on the id")
    public ResponseEntity<MessageResponse> deleteMainOrderItemById(@PathVariable String id) {
        return new ResponseEntity<>(mainOrderItemService.deleteMainOrderItemById(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "find all MainOrder by users principal", description = "List")
    public ResponseEntity<List<MainOrderItemDto>> findAllMainOrderByUsers() {
        return new ResponseEntity<>(mainOrderItemService.findAllMainOrderByUsers()
                .stream().map(mapperForDto::mainOrderItemToDto).toList(), HttpStatus.FOUND);
    }

    @GetMapping("/filter")
    @Operation(summary = "filter all MainOrder by users principal", description = "Filters orders item based on the data")
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
