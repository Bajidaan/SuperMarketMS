package com.bajidan.supermarketms.controllerInterface;

import com.bajidan.supermarketms.dto.user.*;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface UserControllerInterface {
    @PostMapping("/signUp")
    ResponseEntity<String> signUp(@RequestBody SignUp signUp);

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Login login);

    @GetMapping("/get")
    ResponseEntity<List<UserWrapper>> getAllUsers();

    @PostMapping("/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody UpdateStatus status);

    @GetMapping("/checkToken")
    ResponseEntity<String> checkToken();

    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody ChangePassword password);

    @PostMapping("/forgetPassword")
    ResponseEntity<String> forgetPassword(@RequestBody ForgetPassword email);





}
