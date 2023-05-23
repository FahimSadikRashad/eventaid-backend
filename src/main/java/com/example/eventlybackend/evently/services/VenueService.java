package com.example.eventlybackend.evently.services;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.payloads.BookingRequest;
import com.example.eventlybackend.evently.payloads.VenueDto;
import com.example.eventlybackend.evently.payloads.VenueRequest;

import java.util.List;

public interface VenueService {

    /**
     * Creates a new venue with the provided venue information.
     *
     * @param venueRequest The VenueRequest object containing the venue information.
     * @return The VenueDto object representing the created venue.
     */
    VenueDto createVenue(VenueRequest venueRequest);

    /**
     * Updates the venue with the provided venue information for the specified venue ID.
     *
     * @param venueRequest The VenueRequest object containing the updated venue information.
     * @param vid          The ID of the venue to be updated.
     * @return The VenueDto object representing the updated venue.
     */
    VenueDto updateVenue(VenueRequest venueRequest, int vid);

    /**
     * Retrieves the venue by the specified venue ID.
     *
     * @param vid The ID of the venue to be retrieved.
     * @return The VenueDto object representing the retrieved venue.
     */
    VenueDto getVenueById(Integer vid);

    /**
     * Retrieves a list of all venues.
     *
     * @return A list of VenueDto objects representing all venues.
     */
    List<VenueDto> getAllVenues();

    /**
     * Retrieves a list of venues with the specified venue name.
     *
     * @param venueName The name of the venue to be retrieved.
     * @return A list of VenueDto objects representing the retrieved venues.
     */
    List<VenueDto> getVenueByName(String venueName);

    /**
     * Retrieves a list of all unique places.
     *
     * @return A list of strings representing all unique places.
     */
    List<String> getAllPlaces();

    /**
     * Deletes the venue with the specified venue ID.
     *
     * @param vid The ID of the venue to be deleted.
     */
    void deleteVenue(int vid);

    /**
     * Retrieves a list of bookings by the specified username.
     *
     * @param username The username of the user to retrieve the bookings.
     * @return A list of Booking objects associated with the specified username.
     */
    List<Booking> getBookingByUserName(String username);

    /**
     * Retrieves a list of bookings by the specified venue ID.
     *
     * @param vid The ID of the venue to retrieve the bookings.
     * @return A list of Booking objects associated with the specified venue ID.
     */
    List<Booking> getBookingByVenueId(int vid);

    /**
     * Retrieves a list of venues by the specified username.
     *
     * @param username The username of the user to retrieve the venues.
     * @return A list of VenueDto objects associated with the specified username.
     */
    List<VenueDto> getVenuesByUsername(String username);

    /**
     * Checks if a booking can be made based on the provided booking information.
     *
     * @param bookingRequest The BookingRequest object containing the booking information.
     * @return true if the booking can be made, false otherwise.
     */
    Boolean BookingCheck(BookingRequest bookingRequest);

    /**
     * Creates a new booking with the provided booking information.
     *
     * @param bookingRequest The BookingRequest object containing the booking information.
     * @return The Booking object representing the created booking.
     */
    Booking createBooking(BookingRequest bookingRequest);

    /**
     * Updates the booking with the provided booking information for the specified booking ID.
     *
     * @param bookingRequest The BookingRequest object containing the updated booking information.
     * @param bookId         The ID of the booking to be updated.
     * @return The Booking object representing the updated booking.
     */
    Booking updateBooking(BookingRequest bookingRequest, int bookId);

    /**
     * Retrieves a list of bookings by the specified user ID.
     *
     * @param uid The ID of the user to retrieve the bookings.
     * @return A list of Booking objects associated with the specified user ID.
     */
    List<Booking> getBookingByUser(int uid);

    /**
     * Retrieves the booking by the specified booking ID.
     *
     * @param bid The ID of the booking to be retrieved.
     * @return The Booking object representing the retrieved booking.
     */
    Booking getBookingById(int bid);

    /**
     * Deletes the booking with the specified booking ID.
     *
     * @param bid The ID of the booking to be deleted.
     */
    void deleteBookingById(int bid);
}
