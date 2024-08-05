package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.dto.OrderDto;
import com.pizza.PizzaDelivery.entity.request.OrderRequest;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order Management", description = "API for managing orders")
public class OrderController {

    private final OrderService orderService;
    private final MapperForDto orderMapper;

    @Autowired
    public OrderController(OrderService orderService, MapperForDto orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Operation(summary = "Create a new order", description = "Creates a new order based on the provided data")
    @ApiResponse(responseCode = "201", description = "Order created successfully",
            content = @Content(schema = @Schema(implementation = OrderDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(
            @Parameter(description = "Order details", required = true)
            @RequestBody @Valid OrderRequest orderRequest) {
        return new ResponseEntity<>(orderMapper.orderToDto(orderService.createOrder(orderRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders")
    @ApiResponse(responseCode = "302", description = "Orders found",
            content = @Content(schema = @Schema(implementation = OrderDto.class)))
    @ApiResponse(responseCode = "404", description = "No orders found")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrders().stream().map(orderMapper::orderToDto).toList(), HttpStatus.FOUND);
    }

    @Operation(summary = "Get an order by ID", description = "Retrieves an order by its ID")
    @ApiResponse(responseCode = "302", description = "Order found",
            content = @Content(schema = @Schema(implementation = OrderDto.class)))
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(
            @Parameter(description = "ID of the order to retrieve", required = true)
            @PathVariable String id) {
        return new ResponseEntity<>(orderMapper.orderToDto(orderService.getOrderById(id)), HttpStatus.FOUND);
    }
}
