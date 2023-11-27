package com.bajidan.supermarketms.model;

import com.bajidan.supermarketms.dto.UserLogin;
import com.bajidan.supermarketms.dto.UserSignUp;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User {

    @Valid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 200, message = "invalid")
    private String name;

    @Size(min = 11, max = 11, message = "invalid")
    private String contactNumber;

    @Email
    private String email;
    private String password;
    private String role;
    private boolean status;

    public User(UserSignUp signUp) {
        this.name = signUp.name();
        this.contactNumber = signUp.contactNumber();
        this.email = signUp.email();
        this.password = signUp.password();
        this.role = "user";
        this.status = false;
    }

    public boolean isStatus() {
        return status;
    }
}
