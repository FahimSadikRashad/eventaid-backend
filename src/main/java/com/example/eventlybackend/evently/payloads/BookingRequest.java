package com.example.eventlybackend.evently.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class BookingRequest {

    private String place;

    private int userId;
    private int venueId;
    private int eventId;
    private int guests;
    private List<Integer> foodIds;
    private List<Integer> serviceIds;
    private int eventCost;
    private int foodCost;
    private int serviceCost;
    private int totalCost;
    private LocalDate startDate;
    private LocalDate endDate;

//    private LocalDate current_date;

    private String status;
    // getters and setters
}
