package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.Venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing Venue entities.
 */
public interface VenueRepo extends JpaRepository<Venue, Integer> {

    /**
     * Retrieves venues by place.
     *
     * @param venueName The name of the place
     * @return The list of venues matching the place name
     */
    List<Venue> findByPlace(String venueName);

    /**
     * Retrieves venues by user.
     *
     * @param user The user entity
     * @return The list of venues owned by the user
     */
    List<Venue> findByUser(User user);

    /**
     * Retrieves distinct places from the venues.
     *
     * @return The list of distinct places
     */
    @Query(value = "SELECT DISTINCT v.place FROM Venue v")
    List<String> findDistinctPlaces();
}
