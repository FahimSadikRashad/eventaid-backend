package com.example.eventlybackend.evently.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VenueRequest {

    private int userId;
    private VenueDtoSave venueDto;
    private List<EventDto> eventDtoList;
    private List<FoodorServiceDto> foodorServicesList;
}
