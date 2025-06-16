package com.userservice.model;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100, message = "UserName must be between 2 and 100 characters")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 20, message = "Passsword must be between 6 and 20 characters")
    private String password;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String firstName;
    private String lastName;
    private String address;

}