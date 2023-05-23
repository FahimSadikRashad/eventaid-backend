package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.payloads.*;
import com.example.eventlybackend.evently.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

/**
 * The VenueController class is a Spring Boot REST controller that handles requests related to venues and bookings.
 * It provides endpoints for creating, updating, retrieving, and deleting venues, as well as managing bookings.
 */
@RestController
@RequestMapping("/api/venues")
@CrossOrigin("http://localhost:3000")
public class VenueController {

    @Autowired
    private VenueService venueService;

    /**
     * Create a new venue.
     *
     * @param request The VenueRequest object containing the data for the new venue.
     * @return The created venue as a VenueDto object.
     */
    @PostMapping("/events")
    public VenueDto createEvent(@RequestBody VenueRequest request) {
        // Your code to save the entities to database
        VenueDto venueDto = this.venueService.createVenue(request);
        // Return the created event
        return venueDto;
    }

    /**
     * Update an existing venue.
     *
     * @param vid     The ID of the venue to be updated.
     * @param request The VenueRequest object containing the updated data for the venue.
     * @return The updated venue as a VenueDto object.
     */
    @PostMapping("/events/{vid}")
    public VenueDto updateEvent(@PathVariable("vid") int vid, @RequestBody VenueRequest request) {
        // Your code to save the entities to database
        VenueDto venueDto = this.venueService.updateVenue(request, vid);
        // Return the created event
        return venueDto;
    }

    /**
     * Get a venue by its ID.
     *
     * @param vid The ID of the venue.
     * @return The venue as a VenueDto object.
     */
    @GetMapping("/{vid}")
    public VenueDto getVenueById(@PathVariable("vid") int vid) {
        return this.venueService.getVenueById(vid);
    }

    /**
     * Get all venues.
     *
     * @return A list of all venues as VenueDto objects.
     */
    @GetMapping("/all")
    public List<VenueDto> getAllVenues() {
        return this.venueService.getAllVenues();
    }

    /**
     * Get venues by name.
     *
     * @param venueName The name of the venues to retrieve.
     * @return A list of venues with the specified name as VenueDto objects.
     */
    @GetMapping("/all/{vName}")
    public List<VenueDto> getAllVenuesByName(@PathVariable("vName") String venueName) {
        return this.venueService.getVenueByName(venueName);
    }

    /**
     * Get venues by username.
     *
     * @param userName The username associated with the venues to retrieve.
     * @return A ResponseEntity containing a list of venues as VenueDto objects.
     */
    @GetMapping("/username/{userName}")
    public ResponseEntity<List<VenueDto>> getVenueByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(this.venueService.getVenuesByUsername(userName));
    }

    /**
     * Delete a venue by its ID.
     *
     * @param vid The ID of the venue to be deleted.
     * @return A ResponseEntity with an ApiResponse indicating the status of the deletion.
     */
    @DeleteMapping("/del/{vid}")
    public ResponseEntity<ApiResponse> deleteVenue(@PathVariable("vid") int vid) {
        this.venueService.deleteVenue(vid);
        return new ResponseEntity<>(new ApiResponse("Delete success", true, vid), HttpStatus.OK);
    }

    /**
     * Get all distinct place values.
     *
     * @return A ResponseEntity containing a list of distinct place values as strings.
     */
    @GetMapping("/places")
    public ResponseEntity<List<String>> getAllDistinctValues() {
        List<String> places = this.venueService.getAllPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    /**
     * Save a booking.
     *
     * @param bookingRequest The BookingRequest object containing the data for the booking.
     * @return A ResponseEntity with an ApiResponse indicating the status of the booking.
     */
    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse> saveBooking(@RequestBody BookingRequest bookingRequest) {
        if (!this.venueService.BookingCheck(bookingRequest)) {
            return new ResponseEntity<>(new ApiResponse("Booked date filled", false), HttpStatus.OK);
        } else {
            Booking booking = this.venueService.createBooking(bookingRequest);
            return new ResponseEntity<>(new ApiResponse("Booked done", true), HttpStatus.OK);
        }
    }

    /**
     * Get bookings by user ID.
     *
     * @param uid The ID of the user.
     * @return A list of bookings associated with the specified user.
     */
    @GetMapping("/bookings/user/{uid}")
    public List<Booking> getUserBookings(@PathVariable("uid") int uid) {
        return this.venueService.getBookingByUser(uid);
    }

    /**
     * Get a booking by its ID.
     *
     * @param bid The ID of the booking.
     * @return The booking as a Booking object.
     */
    @GetMapping("/bookings/{bid}")
    public Booking getBookingById(@PathVariable("bid") int bid) {
        return this.venueService.getBookingById(bid);
    }

    /**
     * Delete a booking by its ID.
     *
     * @param bid The ID of the booking to be deleted.
     */
    @DeleteMapping("/bookings/{bid}")
    public void deleteBookingById(@PathVariable("bid") int bid) {
        this.venueService.deleteBookingById(bid);
    }

    /**
     * Get bookings by username.
     *
     * @param userName The username associated with the bookings to retrieve.
     * @return A ResponseEntity containing a list of bookings.
     */
    @GetMapping("/bookings/username/{userName}")
    public ResponseEntity<List<Booking>> getNewUser(@PathVariable String userName) {
        return ResponseEntity.ok(this.venueService.getBookingByUserName(userName));
    }

    /**
     * Get bookings by venue ID.
     *
     * @param vid The ID of the venue.
     * @return A ResponseEntity containing a list of bookings associated with the specified venue.
     */
    @GetMapping("/bookings/venue/{vid}")
    public ResponseEntity<List<Booking>> getVenueBookings(@PathVariable int vid) {
        return ResponseEntity.ok(this.venueService.getBookingByVenueId(vid));
    }

    /**
     * Update the status of a booking.
     *
     * @param bid            The ID of the booking to be updated.
     * @param bookingRequest The BookingRequest object containing the updated data for the booking.
     * @return A ResponseEntity containing the updated booking.
     */
    @PutMapping("/booking/{bid}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable("bid") int bid, @RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(this.venueService.updateBooking(bookingRequest, bid));
    }
}
