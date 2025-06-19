package com.patika.service;

import com.patika.dto.request.LoginRequest;
import com.patika.dto.request.RegisterRequest;
import com.patika.dto.response.LoginResponse;
import com.patika.entity.Role;
import com.patika.entity.User;
import com.patika.enums.RoleType;
import com.patika.exception.ConflictException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.UserRepository;
import com.patika.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private final RoleService roleService;
    private  final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, email)));
        return user;

    }

    public void saveUser(RegisterRequest registerRequest) {
        //email
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw  new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS_EXCEPTION, registerRequest.getEmail()));
        }

        //role
        Role role = roleService.findByType(RoleType.ROLE_EMPLOYEE);

        Set<Role>roles = new HashSet<>();
        roles.add(role);

        //şifre
         String encodedPassword =  passwordEncoder.encode(registerRequest.getPassword());

         //user oluştur
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        userRepository.save(user);



    }


    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken aut = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());

        Authentication authenticate =  authenticationManager.authenticate(aut);

        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String token =jwtUtils.generateJwtToken(userDetails);
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return response;
    }
}
