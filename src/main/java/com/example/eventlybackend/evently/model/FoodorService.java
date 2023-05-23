package com.example.eventlybackend.evently.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


/**
 * The FoodorService class represents a food or service offered at a venue.
 */
@Entity
@Table(name = "foods")
@Getter
@Setter
@NoArgsConstructor
public class FoodorService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String serviceName;

    private int serviceCost;

    @Column(columnDefinition = "varchar(255) default 'food'")
    private String what;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    /**
     * Constructs a FoodorService object with the specified name, price, and venue.
     *
     * @param name  the name of the food or service
     * @param price the cost of the food or service
     * @param venue the venue where the food or service is offered
     */
//    @ManyToMany
//    private Set<Booking> bookings;
    public FoodorService(String name, int price, Venue venue) {
        this.serviceName = name;
        this.serviceCost = price;
        this.venue = venue;
    }
}

