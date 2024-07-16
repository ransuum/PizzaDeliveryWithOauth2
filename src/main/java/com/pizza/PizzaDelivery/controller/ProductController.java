package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.dto.ProductDto;
import com.pizza.PizzaDelivery.entity.request.ProductRequest;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final MapperForDto mapper;
    private final ProductService productService;

    @Autowired
    public ProductController(MapperForDto mapper, ProductService productService) {
        this.mapper = mapper;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return new ResponseEntity<>(mapper.productToDto(productService.createProduct(productRequest)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody @Valid ProductRequest productRequest) {
        return new ResponseEntity<>(mapper.productToDto(productService.updateProduct(id, productRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return new ResponseEntity<>(productService.removeProduct(id), HttpStatus.OK);
    }
}
