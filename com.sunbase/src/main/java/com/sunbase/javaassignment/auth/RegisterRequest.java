package com.sunbase.javaassignment.auth;

import com.sunbase.javaassignment.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String street;


    private String city;


    private String state;


    private String country;


    private String phone;

    private Role role;

}
