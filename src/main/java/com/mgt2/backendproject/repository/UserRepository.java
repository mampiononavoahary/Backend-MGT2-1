package com.mgt2.backendproject.repository;

import com.mgt2.backendproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByFirst_name(String username);
}
