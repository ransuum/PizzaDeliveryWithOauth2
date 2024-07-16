package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.dto.OrderDto;
import com.pizza.PizzaDelivery.entity.request.OrderRequest;
import com.pizza.PizzaDelivery.entity.request.UpdatePaymentRequest;
import com.pizza.PizzaDelivery.entity.request.UpdateStatusRequest;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MapperForDto orderMapper;

    @Autowired
    public OrderController(OrderService orderService, MapperForDto orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return new ResponseEntity<>(orderMapper.orderToDto(orderService.createOrder(orderRequest)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrders().stream().map(orderMapper::orderToDto).toList(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        return new ResponseEntity<>(orderMapper.orderToDto(orderService.getOrderById(id)), HttpStatus.FOUND);
    }

    @PutMapping("/{id}/update-order-status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable String id, @RequestBody @Valid UpdateStatusRequest updateStatusRequest) {
        return new ResponseEntity<>(orderMapper.orderToDto(orderService.updateOrderStatus(id, updateStatusRequest)), HttpStatus.OK);
    }

    @PutMapping("/{id}/update-Order-Payment")
    public ResponseEntity<OrderDto> updateOrderPayment(@PathVariable String id, @RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) {
        return new ResponseEntity<>(orderMapper.orderToDto(orderService.updateOrderPayment(id, updatePaymentRequest)), HttpStatus.OK);
    }
}
