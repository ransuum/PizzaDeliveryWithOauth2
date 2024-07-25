package com.pizza.PizzaDelivery.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@AllArgsConstructor
@Data
public class SignUpRequest {

    @Valid
    @Email(message = "Isn't email")
    @NotBlank(message = "Email is blank")
    @Size(max = 40, message = "Email is too long")
    private String email;

    @Valid
    @NotBlank(message = "Password is blank")
    @Size(min = 9, max = 30, message = "Password size should be from 9 to 30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*#?&_-]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

    @Valid
    @NotBlank(message = "firstname is blank")
    @Pattern(regexp = "^[\\p{L}\\p{M} ,.'-]+$", message = "Incorrect first name")
    @JsonProperty("first_name")
    private String firstname;

    @Valid
    @NotBlank(message = "lastname is blank")
    @Pattern(regexp = "^[\\p{L}\\p{M} ,.'-]+$", message = "Incorrect last name")
    @JsonProperty("last_name")
    private String lastname;

    @Valid
    @NotBlank(message = "address is blank")
    @JsonProperty("address")
    private String address;

    @Valid
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    @JsonProperty("phone")
    private String phone;
}
