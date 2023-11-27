package com.bajidan.supermarketms.serviceInterface;

import com.bajidan.supermarketms.dto.UserLogin;
import com.bajidan.supermarketms.dto.UserSignUp;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {
    ResponseEntity<String> signUp(UserSignUp signUp);

    ResponseEntity<String> login(UserLogin login);

    ResponseEntity<List<UserWrapper>> getAllUsers();
}
