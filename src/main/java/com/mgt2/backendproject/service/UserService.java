package com.mgt2.backendproject.service;

import com.mgt2.backendproject.model.entity.User;
import com.mgt2.backendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();

            userToUpdate.setFirst_name(updatedUser.getFirst_name());
            userToUpdate.setLast_name(updatedUser.getLast_name());
            userToUpdate.setEmail(updatedUser.getEmail());

            return userRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    // Methode to valid if user was existing in database
    public boolean isValidUser(String first_name, String password) {
        User user = userRepository.findByFirstName(first_name);

        return user != null && user.getPassword().equals(password);
    }
}
