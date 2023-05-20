package com.example.eventlybackend.evently.payloads;

import com.example.eventlybackend.evently.model.Event;
import com.example.eventlybackend.evently.model.FoodorService;
import com.example.eventlybackend.evently.model.Venue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto {

    private Long id;

    private String place;

//    private Venue venue;
    private  int venue_id;
    private int event_id;

    private Integer guests;
//    private Set<FoodorService> food;

    private LocalDate current_date;

    private String status;

    private int eventCost;
    private int foodCost;
    private int serviceCost;
    private int totalCost;

    private LocalDate startDate;
    private LocalDate endDate;

    private int userId;
}
