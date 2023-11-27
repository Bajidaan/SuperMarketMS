package com.bajidan.supermarketms.wrapper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserWrapper {
    private Integer id;
    private String name;
    private String contactNumber;
    private String email;
    private boolean status;

}
