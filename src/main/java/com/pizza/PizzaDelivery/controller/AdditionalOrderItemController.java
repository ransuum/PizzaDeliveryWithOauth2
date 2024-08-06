package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.AdditionalOrderItem;
import com.pizza.PizzaDelivery.entity.dto.AdditionalItemDto;
import com.pizza.PizzaDelivery.entity.request.AdditionalOrderItemRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.AdditionalOrderItemService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/additional-item")
@Tag(name = "Additional Order Management", description = "API for additional order management")
public class AdditionalOrderItemController {

    private final AdditionalOrderItemService additionalOrderItemService;
    private final MapperForDto mapper;

    @Autowired
    public AdditionalOrderItemController(AdditionalOrderItemService additionalOrderItemService, MapperForDto mapper) {
        this.additionalOrderItemService = additionalOrderItemService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<AdditionalItemDto> createAdditionalOrderItem(
            @Parameter(description = "Additional order item details", required = true)
            @Valid @RequestBody AdditionalOrderItemRequest additionalOrderItemRequest, Authentication authentication) {
        return new ResponseEntity<>(mapper.additionalItemToDto(additionalOrderItemService
                .createAdditionalOrderItem(additionalOrderItemRequest)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalItemDto> updateAdditionalOrderItem(
            @Parameter(description = "ID of the additional order item to update", required = true)
            @PathVariable String id,
            @Parameter(description = "ID of the new product (optional)")
            @RequestParam(required = false) String productId,
            @Parameter(description = "New quantity (optional)")
            @RequestParam(required = false) Integer quantity, Authentication authentication) {
        return new ResponseEntity<>(mapper.additionalItemToDto(additionalOrderItemService
                .updateAdditionalOrderItem(id, productId, quantity)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAdditionalOrderItem(
            @PathVariable String id, Authentication authentication) {
        return new ResponseEntity<>(additionalOrderItemService.deleteAdditionalOrderItem(id), HttpStatus.OK);
    }
}

