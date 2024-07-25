package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.Product;
import com.pizza.PizzaDelivery.entity.request.ProductRequest;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.repo.ProductRepo;
import com.pizza.PizzaDelivery.util.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final Price price;

    @Autowired
    public ProductService(ProductRepo productRepo, Price price) {
        this.productRepo = productRepo;
        this.price = price;
    }

    public Product createProduct(ProductRequest product) {
        Product build = Product.builder()
                .createdAt(Instant.now())
                .info(product.getInfo())
                .categoryForPizza(product.getCategoryForPizza())
                .additionalItem(product.getAdditionalItem())
                .image(product.getImage())
                .discount(product.getDiscount())
                .description(product.getDescription())
                .build();

        if (product.getCategoryForPizza() != null) build.setPrice(price.priceOnPizza(product.getCategoryForPizza()));
        else build.setPrice(price.priceOnAdditional(product.getAdditionalItem()));

        return productRepo.save(build);
    }

    public Product updateProduct(String id, ProductRequest product) {
        Product product1 = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        product1.setInfo(product.getInfo());
        product1.setImage(product.getImage());
        product1.setDiscount(product.getDiscount());
        product1.setDescription(product.getDescription());
        product1.setUpdatedAt(Instant.now());
        return productRepo.save(product1);
    }

    public String removeProduct(String id) {
        productRepo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepo.deleteById(id);
        return id;
    }
}
