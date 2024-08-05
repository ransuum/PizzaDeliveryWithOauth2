package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.RefreshToken;
import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.dto.AuthResponseDto;
import com.pizza.PizzaDelivery.entity.request.SignUpRequest;
import com.pizza.PizzaDelivery.enums.TokenType;
import com.pizza.PizzaDelivery.jwtConfig.JwtTokenGenerator;
import com.pizza.PizzaDelivery.mapper.MapperForDto;
import com.pizza.PizzaDelivery.repo.RefreshTokenRepo;
import com.pizza.PizzaDelivery.repo.UsersRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepo usersRepo;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;
    private final MapperForDto mapper;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto registerUser(SignUpRequest signUpRequest,HttpServletResponse httpServletResponse){

        try{
            log.info("[AuthService:registerUser]User Registration Started with :::{}", signUpRequest);

            Optional<Users> user = usersRepo.findUserByEmail(signUpRequest.getEmail());
            if(user.isPresent()){
                throw new Exception("User Already Exist");
            }
            signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            Users userDetails = mapper.toEntity(signUpRequest);
            userDetails.setRoles("ROLE_USER");
            Authentication authentication = createAuthenticationObject(userDetails);


            // Generate a JWT token
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            Users savedUserDetails = usersRepo.save(userDetails);
            saveUserRefreshToken(userDetails,refreshToken);

            creatRefreshTokenCookie(httpServletResponse,refreshToken);

            log.info("[AuthService:registerUser] User:{} Successfully registered",savedUserDetails.getUsername());
            return   AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(5 * 60)
                    .userName(savedUserDetails.getUsername())
                    .tokenType(TokenType.Bearer)
                    .build();


        }catch (Exception e){
            log.error("[AuthService:registerUser]Exception while registering the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }

    public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
        try {
            var users = usersRepo.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> {
                        log.error("[AuthService:userSignInAuth] User :{} not found", authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND ");
                    });

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            creatRefreshTokenCookie(response, refreshToken);

            //Let's save the refreshToken as well
            saveUserRefreshToken(users, refreshToken);
            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated", users.getUsername());
            return AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(users.getUsername())
                    .tokenType(TokenType.Bearer)
                    .build();


        } catch (BadCredentialsException e) {
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :{}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // in seconds
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    private void saveUserRefreshToken(Users users, String refreshToken) {
        var refreshTokenEntity = RefreshToken.builder()
                .user(users)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        if (!authorizationHeader.startsWith(TokenType.Bearer.name())) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please verify your token type");
        }

        final String refreshToken = authorizationHeader.substring(7);

        //Find refreshToken from database and should not be revoked : Same thing can be done through filter.
        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token revoked"));

        Users users = refreshTokenEntity.getUser();

        //Now create the Authentication object
        Authentication authentication = createAuthenticationObject(users);

        //Use the authentication object to generate new accessToken as the Authentication object that we will have may not contain correct role.
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(users.getUsername())
                .tokenType(TokenType.Bearer)
                .build();
    }

    private static Authentication createAuthenticationObject(Users users) {
        // Extract user details from UserDetailsEntity
        String username = users.getEmail();
        String password = users.getPassword();
        String roles = users.getRoles();

        // Extract authorities from roles (comma-separated)
        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }

}
