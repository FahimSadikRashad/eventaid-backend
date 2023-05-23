package com.example.eventlybackend.evently.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * The Booking class represents a booking made by a user for a venue and event.
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String place;

    @JsonIgnoreProperties({"events", "foods", "bookings","user"})
    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "id")
    private Venue venue;
//    @JsonBackReference

    @JsonIgnoreProperties({"venue"})
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    private Integer guests;
//    @JsonBackReference
    @JsonIgnoreProperties({"venue"})
    @ManyToMany
    @JoinTable(name = "booking_foods",
            joinColumns = {@JoinColumn(name = "booking_id")},
            inverseJoinColumns = {@JoinColumn(name = "food_id")})
    private Set<FoodorService> food;


    private int eventCost;
    private int foodCost;
    private int serviceCost;
    private int totalCost;

    private LocalDate startDate;
    private LocalDate endDate;

//    @Column( columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
//    private LocalDate current_date;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
    private String status;

    @JsonIgnoreProperties({"venues"})
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
