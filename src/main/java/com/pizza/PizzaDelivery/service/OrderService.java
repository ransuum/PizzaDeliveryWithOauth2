package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.Orders;
import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.request.OrderRequest;
import com.pizza.PizzaDelivery.enums.PayStatus;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final MapperForDto mapper;

    @Autowired
    public OrderService(OrderRepo orderRepo, MapperForDto mapper) {
        this.orderRepo = orderRepo;
        this.mapper = mapper;
    }

    public Orders createOrder(OrderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) authentication.getPrincipal();

        Orders orders = Orders.builder()
                .createdAt(LocalDateTime.now())
                .payStatus(PayStatus.NOT_PAID)
                .comments(orderRequest.getComments())
                .change(false)
                .paymentMethod(orderRequest.getPaymentMethod())
                .userInfo(user)
                .build();

        return orderRepo.save(orders);
    }

    public List<Orders> getOrders() {
        return orderRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Orders getOrderById(String id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Orders not found by " + id));
    }
}
