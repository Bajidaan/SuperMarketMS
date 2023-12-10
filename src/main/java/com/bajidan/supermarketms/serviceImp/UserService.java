package com.bajidan.supermarketms.serviceImp;

import com.bajidan.supermarketms.dto.user.*;
import com.bajidan.supermarketms.jwt.JwtFilter;
import com.bajidan.supermarketms.model.User;
import com.bajidan.supermarketms.repository.UserRepository;
import com.bajidan.supermarketms.serviceInterface.UserServiceInterface;
import com.bajidan.supermarketms.util.EmailUtil;
import com.bajidan.supermarketms.util.HttpMessageUtil;
import com.bajidan.supermarketms.util.JwtUtil;
import com.bajidan.supermarketms.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final CustomerUserServiceDetail userServiceDetail;

    private final JwtUtil jwtUtil;

    private final JwtFilter jwtFilter;

    private final EmailUtil emailUtil;

    @Override
    public ResponseEntity<String> signUp(SignUp signUp) {
        try {
            User newUser = new User(signUp);
            Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());

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
    public ResponseEntity<String> login(Login login) {
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

        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(UpdateStatus status) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> user = userRepository.findById(status.id());

                if (user.isPresent()) {
                    user.get().setStatus(status.status());
                    userRepository.save(user.get());
                    sendMailToAllAdmin(user.get().getEmail(), status.status());
                    return HttpMessageUtil.successful("Successful...");
                } else {
                    return HttpMessageUtil.badRequest("user not found");
                }
            }

            return HttpMessageUtil.unAuthorized("Can't access. Only for admin");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePassword password) {
        try {
            User currentUser = userRepository.findByEmail(jwtFilter.getCurrentUsername()).orElseThrow();

            if (password.oldPassword().equals(currentUser.getPassword())) {
                currentUser.setPassword(password.newPassword());
                userRepository.save(currentUser);

                return HttpMessageUtil.successful("password changed");
            }
            return HttpMessageUtil.badRequest("wrong password");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<String> forgetPassword(ForgetPassword email) {
        try {
            User user = userRepository.findByEmail(email.email()).orElseThrow(
                    () -> new UsernameNotFoundException(email.email() + " does not exist")
            );

             emailUtil.sendPasswordMail(user.getEmail(), "Password Recovery", user.getPassword());
        return HttpMessageUtil.successful("Check your mail for your password");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }

    private List<UserWrapper> allUsers() {
        List<User> usersList = userRepository.findAll();

        return usersList
                .stream()
                .map(user -> new UserWrapper(user.getId(), user.getName(),user.getContactNumber(), user.getEmail(), user.isStatus()))
                .sorted((x, y) -> x.getId() - y.getId()).toList();
    }

    private void sendMailToAllAdmin(String user, boolean isStatus) {
        String currentAmin = jwtFilter.getCurrentUsername();


        List<String> allAdminMail = userRepository.findAll()
                .stream()
                .filter(x ->
                        x.getRole().equalsIgnoreCase("admin") &&
                                !x.getEmail().equals(currentAmin))
                .map(User::getEmail).toList();

        String subject;
        String text ;
        if (isStatus) {
            subject = "Account Approved";
            text = "USER: " + user + "\n\tis approved by \nADMIN:- " + currentAmin;
        } else {
            subject = "Account Disable";
            text = "USER: " + user + "\n\tis disable by \nADMIN:- " + currentAmin;
        }

        emailUtil.sendSimpleMessage(currentAmin, subject, text, allAdminMail);


    }
}
