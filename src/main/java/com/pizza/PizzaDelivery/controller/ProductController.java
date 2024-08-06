package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.dto.ProductDto;
import com.pizza.PizzaDelivery.entity.request.ProductRequest;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product Management", description = "API for managing products")
@RequiredArgsConstructor
public class ProductController {

    private final MapperForDto mapper;
    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(
            @Parameter(description = "Product details", required = true)
            @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(mapper.productToDto(productService.createProduct(productRequest)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER' or 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable String id,
            @Parameter(description = "Updated product details", required = true)
            @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(mapper.productToDto(productService.updateProduct(id, productRequest)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER' or 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable String id) {
        return new ResponseEntity<>(productService.removeProduct(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER' or 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        return new ResponseEntity<>(mapper.productToDto(productService.getProductById(id)), HttpStatus.FOUND);
    }
}
