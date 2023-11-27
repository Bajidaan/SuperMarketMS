package com.bajidan.supermarketms.serviceImp;

import com.bajidan.supermarketms.dto.UserLogin;
import com.bajidan.supermarketms.dto.UserSignUp;
import com.bajidan.supermarketms.jwt.JwtFilter;
import com.bajidan.supermarketms.model.User;
import com.bajidan.supermarketms.repository.UserRepository;
import com.bajidan.supermarketms.serviceInterface.UserServiceInterface;
import com.bajidan.supermarketms.util.HttpMessageUtil;
import com.bajidan.supermarketms.util.JwtUtil;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final CustomerUserServiceDetail userServiceDetail;

    private final JwtUtil jwtUtil;

    private final JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUp(UserSignUp signUp) {
        try {
            User newUser = new User(signUp);
            Optional<User> existingUser = userRepository.findAllByEmail(newUser.getEmail());

            if (existingUser.isEmpty()) {
                userRepository.save(newUser);
                return HttpMessageUtil.successful("Successfully signed up");
            }

            return HttpMessageUtil.getResponse("Email already exist", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<String> login(UserLogin login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.email(), login.password()));

            if (authentication.isAuthenticated()) {
                User user = userServiceDetail.getUser();
                if (user.isStatus()) {
                    return new ResponseEntity<>(String.format("{\"token\": \"%s\"}",
                            jwtUtil.generateToken(user.getEmail(), user.getRole())), HttpStatus.OK);
                } else {
                    return  HttpMessageUtil.badRequest("wait for admin approval");
                }
            } else {
                return HttpMessageUtil.badRequest("Wrong username/password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(allUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<UserWrapper> allUsers() {
        List<User> usersList = userRepository.findAll();

        return usersList
                .stream()
                .map(user -> new UserWrapper(user.getId(), user.getName(),user.getContactNumber(), user.getEmail(), user.isStatus()))
                .sorted((x, y) -> x.getId() - y.getId()).toList();
    }
}
