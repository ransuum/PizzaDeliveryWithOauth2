package com.pizza.PizzaDelivery;

import com.pizza.PizzaDelivery.security.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class PizzaDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaDeliveryApplication.class, args);
	}

}
