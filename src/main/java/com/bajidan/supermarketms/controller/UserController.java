package com.bajidan.supermarketms.controller;

import com.bajidan.supermarketms.controllerInterface.UserControllerInterface;
import com.bajidan.supermarketms.dto.UserLogin;
import com.bajidan.supermarketms.dto.UserSignUp;
import com.bajidan.supermarketms.model.User;
import com.bajidan.supermarketms.repository.UserRepository;
import com.bajidan.supermarketms.serviceImp.UserService;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerInterface {

    private final UserService userService;

    private final UserRepository repository;

    @Override
    public ResponseEntity<String> signUp(@Valid UserSignUp signUp) {
        return userService.signUp(signUp);
    }

    @Override
    public ResponseEntity<String> login(UserLogin login) {
        return userService.login(login);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName =  ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
