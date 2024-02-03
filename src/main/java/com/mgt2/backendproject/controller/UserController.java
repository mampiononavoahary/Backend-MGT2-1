package com.mgt2.backendproject.controller;

import com.mgt2.backendproject.model.entity.User;
import com.mgt2.backendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/Users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
