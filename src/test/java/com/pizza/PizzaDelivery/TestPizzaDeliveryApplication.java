package com.pizza.PizzaDelivery;

import org.springframework.boot.SpringApplication;

public class TestPizzaDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.from(PizzaDeliveryApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
