package com.example.eventlybackend.evently.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The Venue class represents a venue entity.
 */
@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties("user")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String venueName;

    private String place;

    private String contact;
    @JsonManagedReference
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<FoodorService> foods = new ArrayList<>();

//
    @JsonBackReference
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Booking> bookings=new ArrayList<>();

//    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Constructs a Venue object with the specified name, place, and contact.
     *
     * @param name    the name of the venue
     * @param place   the place where the venue is located
     * @param contact the contact information of the venue
     */
    public Venue(String name, String place, String contact) {
        this.venueName = name;
        this.place = place;
        this.contact = contact;
    }
}
