package com.example.eventlybackend.evently.payloads;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.model.Venue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserDto class represents the data transfer object for user information.
 */
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters !!")
    private String name;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
//    @Pattern(regexp = "")
    private String password;
    @NotEmpty
    private String role;

    private List<VenueDto> venues = new ArrayList<>();



}
