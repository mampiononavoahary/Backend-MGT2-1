package com.mgt2.backendproject.service;

import com.mgt2.backendproject.model.entity.LoginRequest;
import com.mgt2.backendproject.model.entity.Role;
import com.mgt2.backendproject.model.entity.User;
import com.mgt2.backendproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (request.getPassword().equals(user.getPassword())) {
                return jwtService.generateToken(user);
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @SuppressWarnings("null")
    public User getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public User createUser(User user) {
        user.setRole(Role.USER);
        User save = userRepository.save(user);
        new InMemoryUserDetailsManager(save);
        return save;
    }

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
