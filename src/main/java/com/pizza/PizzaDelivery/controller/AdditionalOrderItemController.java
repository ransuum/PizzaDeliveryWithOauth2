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

    @Operation(summary = "Create an additional order item",
            description = "Creates a new additional order item based on the provided data")
    @ApiResponse(responseCode = "201", description = "Additional order item created successfully",
            content = @Content(schema = @Schema(implementation = AdditionalItemDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<AdditionalItemDto> createAdditionalOrderItem(
            @Parameter(description = "Additional order item details", required = true)
            @Valid @RequestBody AdditionalOrderItemRequest additionalOrderItemRequest) {
        return new ResponseEntity<>(mapper.additionalItemToDto(additionalOrderItemService
                .createAdditionalOrderItem(additionalOrderItemRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an additional order item",
            description = "Updates an existing additional order item")
    @ApiResponse(responseCode = "200", description = "Additional order item updated successfully",
            content = @Content(schema = @Schema(implementation = AdditionalItemDto.class)))
    @ApiResponse(responseCode = "404", description = "Additional order item not found")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<AdditionalItemDto> updateAdditionalOrderItem(
            @Parameter(description = "ID of the additional order item to update", required = true)
            @PathVariable String id,
            @Parameter(description = "ID of the new product (optional)")
            @RequestParam(required = false) String productId,
            @Parameter(description = "New quantity (optional)")
            @RequestParam(required = false) Integer quantity) {
        return new ResponseEntity<>(mapper.additionalItemToDto(additionalOrderItemService
                .updateAdditionalOrderItem(id, productId, quantity)), HttpStatus.OK);
    }

    @Operation(summary = "Delete an additional order item",
            description = "Deletes an existing additional order item")
    @ApiResponse(responseCode = "200", description = "Additional order item deleted successfully",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @ApiResponse(responseCode = "404", description = "Additional order item not found")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<MessageResponse> deleteAdditionalOrderItem(
            @Parameter(description = "ID of the additional order item to delete", required = true)
            @PathVariable String id) {
        return new ResponseEntity<>(additionalOrderItemService.deleteAdditionalOrderItem(id), HttpStatus.OK);
    }
}

