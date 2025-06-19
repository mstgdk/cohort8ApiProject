package com.patika.controller;

import com.patika.dto.request.LoginRequest;
import com.patika.dto.request.RegisterRequest;
import com.patika.dto.response.LoginResponse;
import com.patika.dto.response.PatikaResponse;
import com.patika.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<PatikaResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);

     PatikaResponse response = new PatikaResponse();
     response.setSuccess(true);
     response.setMessage("User saved successfully");

     return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
      LoginResponse response = userService.login(loginRequest);

      return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
