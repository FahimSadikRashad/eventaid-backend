package com.example.eventlybackend.evently.services;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.payloads.BookingRequest;
import com.example.eventlybackend.evently.payloads.VenueDto;
import com.example.eventlybackend.evently.payloads.VenueRequest;

import java.util.List;

public interface VenueService {
    VenueDto createVenue(VenueRequest venueRequest);
    VenueDto getVenueById(Integer vid);

    List<VenueDto> getAllVenues();

    List<VenueDto> getVenueByName(String venueName);

    //find all unique places
    List<String> getAllPlaces();

    //delete a venue based on id
    void deleteVenue(int vid);
    //delete an event based on id
    //delete a food or Service based on id

    Boolean BookingCheck(BookingRequest bookingRequest);

    Booking createBooking(BookingRequest bookingRequest);

    List<Booking> getBookingByUser(int uid);
    Booking getBookingById(int bid);

    void deleteBookingById(int bid);
}
