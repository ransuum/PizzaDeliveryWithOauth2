package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.request.SignUpRequest;
import com.pizza.PizzaDelivery.exception.InvalidJwtException;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.repo.UsersRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = usersRepo.findByEmail(email);
        return user;
    }

    public Users signUp(SignUpRequest data) throws InvalidJwtException {
        if (usersRepo.findByEmail(data.getEmail()) != null) {
            throw new InvalidJwtException("Email already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Users newUser = new Users(data.getEmail(), encryptedPassword);
        newUser.setCreatedAt(Instant.now());
        return usersRepo.save(newUser);
    }

    public void logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("Authorization", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
    }
}
