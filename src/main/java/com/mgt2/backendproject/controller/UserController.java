package com.mgt2.backendproject.controller;

import com.mgt2.backendproject.model.entity.LoginRequest;
import com.mgt2.backendproject.model.entity.User;
import com.mgt2.backendproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/Users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/User/{id}")
    public Optional<User> getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/User")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/Login")
    public User logUser(@RequestBody User user) {
        return user;
    }

    @PostMapping("/Login/valid")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (userService.isValidUser(username, password)) {
            return new ResponseEntity<>("Authentification with succes", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Name ou password invalid", HttpStatus.OK);
        }
    }

    @PostMapping("/User/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>("User created with success", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on creating user", HttpStatus.OK);
        }
    }


    @PutMapping("/User/update/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/User/Delete/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }
}
