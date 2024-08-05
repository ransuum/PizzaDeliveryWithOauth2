package com.pizza.PizzaDelivery;

import com.pizza.PizzaDelivery.security.RSA.RSAKeyRecord;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class PizzaDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaDeliveryApplication.class, args);
	}

	@Bean
	public ModelMapper beanModelMapper(){
		return new ModelMapper();
	}
}
