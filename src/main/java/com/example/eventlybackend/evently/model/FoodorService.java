package com.example.eventlybackend.evently.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    public FoodorService(String name, int price, Venue venue) {
        this.serviceName = name;
        this.serviceCost = price;
        this.venue = venue;
    }
}

