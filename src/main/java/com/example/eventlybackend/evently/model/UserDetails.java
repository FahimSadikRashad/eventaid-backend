package com.example.eventlybackend.evently.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userDetails")
@Getter
@Setter
@NoArgsConstructor
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String phone;
    private String address;

    @Lob
    private byte[] picture;

    public UserDetails  (User user, String firstname, String lastname, String email, String gender, String phone, String address, byte[] picture) {
        this.user = user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.picture = picture;
    }

    // getters and setters omitted for brevity
}
