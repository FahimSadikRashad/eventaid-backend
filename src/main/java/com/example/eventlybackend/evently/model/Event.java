package com.example.eventlybackend.evently.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Event class represents an event that can be hosted at a venue.
 */
@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String eventName;

    private int eventCost;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    /**
     * Constructs an Event object with the specified name, price, and venue.
     *
     * @param name  the name of the event
     * @param price the cost of the event
     * @param venue the venue where the event will be hosted
     */

    public Event(String name, int price, Venue venue) {
        this.eventName = name;
        this.eventCost = price;
        this.venue = venue;
    }
}
