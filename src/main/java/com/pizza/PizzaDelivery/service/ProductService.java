package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.Product;
import com.pizza.PizzaDelivery.entity.request.ProductRequest;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product createProduct(ProductRequest product) {
        return productRepo.save(Product.builder()
                .createdAt(Instant.now())
                .info(product.getInfo())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .discount(product.getDiscount())
                .description(product.getDescription())
                .build());
    }

    public Product updateProduct(String id, ProductRequest product) {
        Product product1 = productRepo.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
        product1.setInfo(product.getInfo());
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setImage(product.getImage());
        product1.setDiscount(product.getDiscount());
        product1.setDescription(product.getDescription());
        product1.setUpdatedAt(Instant.now());
        return productRepo.save(product1);
    }

    public String removeProduct(String id) {
        productRepo.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
        productRepo.deleteById(id);
        return id;
    }
}
