package com.example.eventlybackend.evently.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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


    private int userId;
}
