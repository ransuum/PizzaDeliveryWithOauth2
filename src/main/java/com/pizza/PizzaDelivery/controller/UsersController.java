package com.pizza.PizzaDelivery.controller;

import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.dto.UsersDto;
import com.pizza.PizzaDelivery.entity.request.SignInRequest;
import com.pizza.PizzaDelivery.entity.request.SignUpRequest;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.repo.UsersRepo;
import com.pizza.PizzaDelivery.security.TokenProvider;
import com.pizza.PizzaDelivery.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UsersController {

    private final UsersService usersService;
    private final MapperForDto mapper;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UsersRepo usersRepo;

    @Autowired
    public UsersController(UsersService usersService, MapperForDto mapper, UsersRepo usersRepo,
                           TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.usersRepo = usersRepo;
        this.mapper = mapper;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDto> registerUser(@RequestBody @Valid SignUpRequest sign){
        return new ResponseEntity<>(mapper.userToDto(usersService.signUp(sign)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid SignInRequest sign, HttpServletResponse httpServletResponse) throws NotFoundException {
        Users users = usersRepo.findUserByEmail(sign.getEmail())
                .orElseThrow(()-> new NotFoundException("user with this email not found"));

        var usernamePassword = new UsernamePasswordAuthenticationToken(sign.getEmail(), sign.getPassword());
        var authUser = authenticationManager.authenticate(usernamePassword);
        httpServletResponse.addCookie(tokenProvider.generateAuthorizationCookie(users));
        return ResponseEntity.ok("Authorize");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse) {
        usersService.logout(httpServletResponse);
        return ResponseEntity.ok("Logout");
    }
}
