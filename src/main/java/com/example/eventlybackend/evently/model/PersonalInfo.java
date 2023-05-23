package com.example.eventlybackend.evently.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The PersonalInfo class represents the personal information of a user.
 */
@Entity
@Table(name = "personalInfo")
@Getter
@Setter
@NoArgsConstructor
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String phone;
    private String address;
    /**
     * Constructs a PersonalInfo object with the specified user, first name, last name, email, gender, phone, and address.
     *
     * @param user      the user associated with the personal information
     * @param firstname the first name of the user
     * @param lastname  the last name of the user
     * @param email     the email address of the user
     * @param gender    the gender of the user
     * @param phone     the phone number of the user
     * @param address   the address of the user
     * @param picture   the profile picture of the user
     */

    public PersonalInfo  (User user, String firstname, String lastname, String email, String gender, String phone, String address, byte[] picture) {
        this.user = user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.address = address;

    }

    // getters and setters omitted for brevity
}
