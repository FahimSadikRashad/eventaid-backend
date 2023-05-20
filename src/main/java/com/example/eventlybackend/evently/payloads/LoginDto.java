package com.example.eventlybackend.evently.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;

    // Constructors, getters, and setters
}