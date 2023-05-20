package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.Venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VenueRepo extends JpaRepository<Venue,Integer> {
    List<Venue> findByPlace(String venueName);

    List<Venue> findByUser(User User);

    @Query(value = "SELECT DISTINCT v.place FROM Venue v")
    List<String> findDistinctPlaces();
}
