package com.example.eventlybackend.evently.services;

import com.example.eventlybackend.evently.payloads.VenueDto;
import com.example.eventlybackend.evently.payloads.VenueRequest;

import java.util.List;

public interface VenueService {
    VenueDto createVenue(VenueRequest venueRequest);
    VenueDto getVenueById(Integer vid);

    List<VenueDto> getAllVenues();

    List<VenueDto> getVenueByName(String venueName);
}
