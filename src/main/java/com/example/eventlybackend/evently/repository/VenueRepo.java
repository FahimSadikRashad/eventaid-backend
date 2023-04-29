package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.Venue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepo extends JpaRepository<Venue,Integer> {
    List<Venue> findByPlace(String venueName);
}
