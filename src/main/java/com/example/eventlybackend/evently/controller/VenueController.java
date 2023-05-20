package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.payloads.*;
import com.example.eventlybackend.evently.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin("http://localhost:3000")
public class VenueController {
    @Autowired
    private VenueService venueService;
    @PostMapping("/events")
    public VenueDto createEvent(@RequestBody VenueRequest request) {

        // Your code to save the entities to database
        VenueDto venueDto=this.venueService.createVenue(request);
        // Return the created event
        return venueDto;
    }
    @GetMapping("/{vid}")
    public VenueDto getVenueById(@PathVariable("vid") int vid){
        return this.venueService.getVenueById(vid);
    }
    @GetMapping("/all")
    public List<VenueDto> getAllVenues(){
        return this.venueService.getAllVenues();
    }
    @GetMapping("/all/{vName}")
    public List<VenueDto> getAllVenuesByName(@PathVariable("vName") String venueName){
        return this.venueService.getVenueByName(venueName);
    }
    @GetMapping("/username/{userName}")
    public ResponseEntity<List<VenueDto>> getVenueByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(this.venueService.getVenuesByUsername(userName));
    }
    @DeleteMapping("/del/{vid}")
    public ResponseEntity<ApiResponse> deleteVenue(@PathVariable("vid") int vid){
        this.venueService.deleteVenue(vid);
        return new ResponseEntity<>(new ApiResponse("Delete success",true,vid),HttpStatus.OK);
    }

    @GetMapping("/places")
    public ResponseEntity<List<String> > getAllDistinctValues(){
        List<String> places =this.venueService.getAllPlaces();
        return new ResponseEntity<>(places,HttpStatus.OK);
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse> saveBooking(@RequestBody BookingRequest bookingRequest) {
//        if(!this.venueService.BookingCheck(bookingRequest)){
//            return new ResponseEntity<>(new ApiResponse("Booked date filed",false),HttpStatus.OK);
//        }
//        else{
        System.out.println(bookingRequest.getStatus());
            Booking booking=this.venueService.createBooking(bookingRequest);
            System.out.println(booking);
            return new ResponseEntity<>(new ApiResponse("Booked done",true),HttpStatus.OK);
//        }
    }
    @GetMapping("/bookings/user/{uid}")
    public  List<Booking> getUserBookings(@PathVariable("uid") int uid){
        return this.venueService.getBookingByUser(uid);
    }

    @GetMapping("/bookings/{bid}")
    public  Booking getBookingById(@PathVariable("bid") int bid){
        return this.venueService.getBookingById(bid);
    }

    @DeleteMapping("/bookings/{bid}")
    public  void deleteBookingById(@PathVariable("bid") int bid){
         this.venueService.deleteBookingById(bid);
    }

    @GetMapping("/bookings/username/{userName}")
    public ResponseEntity<List<Booking>> getNewUser(@PathVariable String userName) {
        return ResponseEntity.ok(this.venueService.getBookingByUserName(userName));
    }
    @GetMapping("/bookings/venue/{vid}")
    public ResponseEntity<List<Booking>> getVenueBookings(@PathVariable int vid) {
        return ResponseEntity.ok(this.venueService.getBookingByVenueId(vid));
    }

}
