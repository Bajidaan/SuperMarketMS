package com.bajidan.supermarketms.repository;

import com.bajidan.supermarketms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findAllByEmail(String email);
}
