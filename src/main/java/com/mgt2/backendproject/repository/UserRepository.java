package com.mgt2.backendproject.repository;

import com.mgt2.backendproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
