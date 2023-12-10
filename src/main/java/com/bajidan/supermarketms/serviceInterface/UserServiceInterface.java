package com.bajidan.supermarketms.serviceInterface;

import com.bajidan.supermarketms.dto.user.*;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {
    ResponseEntity<String> signUp(SignUp signUp);

    ResponseEntity<String> login(Login login);

    ResponseEntity<List<UserWrapper>> getAllUsers();

    ResponseEntity<String> updateStatus(UpdateStatus status);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(ChangePassword password);

    ResponseEntity<String> forgetPassword(ForgetPassword email);
}
