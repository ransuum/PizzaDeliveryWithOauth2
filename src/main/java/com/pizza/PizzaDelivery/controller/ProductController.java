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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product Management", description = "API for managing products")
public class ProductController {

    private final MapperForDto mapper;
    private final ProductService productService;

    @Autowired
    public ProductController(MapperForDto mapper, ProductService productService) {
        this.mapper = mapper;
        this.productService = productService;
    }

    @Operation(summary = "Add a new product", description = "Creates a new product based on the provided data")
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(
            @Parameter(description = "Product details", required = true)
            @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(mapper.productToDto(productService.createProduct(productRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a product", description = "Updates an existing product based on the provided data")
    @ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = @Content(schema = @Schema(implementation = ProductDto.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable String id,
            @Parameter(description = "Updated product details", required = true)
            @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(mapper.productToDto(productService.updateProduct(id, productRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Delete a product", description = "Deletes an existing product")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully",
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable String id) {
        return new ResponseEntity<>(productService.removeProduct(id), HttpStatus.OK);
    }
}
