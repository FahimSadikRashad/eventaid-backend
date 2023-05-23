package com.example.eventlybackend.evently.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user in the system.
 */
@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name", nullable = false,length = 255)
    private String name;
    private String email;
    private String password;
    private String role;


    @JsonManagedReference
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties({"user","bookings"})
    private List<Venue> venues = new ArrayList<>();


}
