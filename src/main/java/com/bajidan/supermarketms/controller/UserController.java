package com.bajidan.supermarketms.controller;

import com.bajidan.supermarketms.controllerInterface.UserControllerInterface;
import com.bajidan.supermarketms.dto.user.*;
import com.bajidan.supermarketms.serviceImp.UserService;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerInterface {

    private final UserService userService;



    @Override
    public ResponseEntity<String> signUp(@Valid SignUp signUp) {
        return userService.signUp(signUp);
    }

    @Override
    public ResponseEntity<String> login(Login login) {
        return userService.login(login);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public ResponseEntity<String> updateStatus(UpdateStatus status) {
        return userService.updateStatus(status);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return userService.checkToken();
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePassword password) {
        return userService.changePassword(password);
    }

    @Override
    public ResponseEntity<String> forgetPassword(ForgetPassword email) {
        return userService.forgetPassword(email);
    }

}
