package com.patika.service;

import com.patika.entity.User;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, email)));
        return user;

    }
}
