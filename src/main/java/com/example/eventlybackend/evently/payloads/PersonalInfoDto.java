package com.example.eventlybackend.evently.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalInfoDto {

    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String phone;
    private String address;
}
