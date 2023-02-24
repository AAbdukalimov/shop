package com.example.shop.restcontrollers;

import com.example.shop.dto.authorize.AuthRequest;
import com.example.shop.dto.authorize.AuthResponse;
import com.example.shop.dto.authorize.RegistrationRequest;
import com.example.shop.entities.User;
import com.example.shop.service.user.UserService;
import com.example.shop.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthRestController {
    private UserService userService;

    @PostMapping("/rest/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        User user = userService.findByUserNameAndPassword(authRequest.getUserName(), authRequest.getPassword());
        String token = JwtUtils.generateToken(user.getUserName());
        return AuthResponse.builder().token(token).build();
    }

    @PostMapping("/rest/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .userName(registrationRequest.getUserName())
                .password(registrationRequest.getPassword())
                .build();
        userService.create(user);

    }
}
