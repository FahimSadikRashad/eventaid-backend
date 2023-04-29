package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.payloads.VenueDto;
import com.example.eventlybackend.evently.payloads.VenueRequest;
import com.example.eventlybackend.evently.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
