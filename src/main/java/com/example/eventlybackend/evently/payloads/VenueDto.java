package com.example.eventlybackend.evently.payloads;


import com.example.eventlybackend.evently.model.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The VenueDto class represents the data transfer object for venue information.
 */
@Getter
@Setter
@NoArgsConstructor
public class VenueDto {

    private  int id;
    private String venueName;

    private String place;

    private String contact;


    private List<EventDto> events = new ArrayList<>();


    private List<FoodorServiceDto> foods = new ArrayList<>();
    private List<Booking> booking=new ArrayList<>();

    private int userId;
}
