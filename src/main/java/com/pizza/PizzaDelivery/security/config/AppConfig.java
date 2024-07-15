package com.pizza.PizzaDelivery.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String frontendDomain;
    private Security security;

    // Getters and setters

    public static class Security {
        private Jwt jwt;

        // Getters and setters

        public static class Jwt {
            private Token token;

            // Getters and setters

            public static class Token {
                private String secretKey;

                // Getter and setter for secretKey
            }
        }
    }
}
