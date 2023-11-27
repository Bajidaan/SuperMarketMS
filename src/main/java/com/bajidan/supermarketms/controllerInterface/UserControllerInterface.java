package com.bajidan.supermarketms.controllerInterface;

import com.bajidan.supermarketms.dto.UserLogin;
import com.bajidan.supermarketms.dto.UserSignUp;
import com.bajidan.supermarketms.model.User;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface UserControllerInterface {
    @PostMapping("/signUp")
    ResponseEntity<String> signUp(@RequestBody UserSignUp  signUp);

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserLogin login);

    @RequestMapping("/get")
    ResponseEntity<List<UserWrapper>> getAllUsers();

}
