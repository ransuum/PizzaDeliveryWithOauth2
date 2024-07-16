package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.Order;
import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.request.OrderRequest;
import com.pizza.PizzaDelivery.entity.request.UpdatePaymentRequest;
import com.pizza.PizzaDelivery.entity.request.UpdateStatusRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order createOrder(OrderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof Users authenticatedUser)) {
            throw new IllegalStateException("Authenticated user is not of type Users");
        }

        Order order = Order.builder()
                .userInfo(Order.UserInfo.builder()
                        .name(orderRequest.getUserInfo().getName() != null ? orderRequest.getUserInfo().getName() : authenticatedUser.getName())
                        .phone(orderRequest.getUserInfo().getPhone() != null ? orderRequest.getUserInfo().getPhone() : authenticatedUser.getPhone())
                        .email(orderRequest.getUserInfo().getEmail() != null ? orderRequest.getUserInfo().getEmail() : authenticatedUser.getEmail())
                        .build())
                .userId(authenticatedUser.getId())
                .comments(orderRequest.getComments())
                .additionalOrder(orderRequest.getAdditionalOrder())
                .mainOrder(orderRequest.getMainOrder())
                .paymentMethod(orderRequest.getPaymentMethod())
                .totalPrice(orderRequest.getTotalPrice())
                .userAddress(orderRequest.getUserAddress())
                .payStatus(orderRequest.getPayStatus())
                .change(orderRequest.getChange())
                .status(orderRequest.getStatus() != null ? orderRequest.getStatus() : "Отримано")
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepo.save(order);
    }

    public ResponseEntity<?> getOrders() {
        try {
            return ResponseEntity.ok(orderRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("INTERNAL SERVER ERROR"));
        }
    }

    public Order getOrderById(String id) {
        return orderRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Order not found by " + id));
    }

    public Order updateOrderStatus(String id, UpdateStatusRequest updateStatusRequest) {
        Order order = orderRepo.findById(id).orElseThrow(()-> new NotFoundException("Order not found by " + id));
        order.setStatus(updateStatusRequest.getStatus());
        return order;
    }

    public Order updateOrderPayment(String id, UpdatePaymentRequest updatePaymentRequest) {
        Order order = orderRepo.findById(id).orElseThrow(()-> new NotFoundException("Order not found by " + id));
        order.setStatus(updatePaymentRequest.getPayStatus());
        return order;
    }
}
