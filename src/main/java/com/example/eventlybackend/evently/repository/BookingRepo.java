package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing Booking entities.
 */
public interface BookingRepo extends JpaRepository<Booking, Integer> {

    /**
     * Retrieves the count of previous bookings that include the specified start date.
     *
     * @param startDate The start date to check
     * @return The count of previous bookings
     */
    @Query(value = "SELECT COUNT(*) FROM bookings WHERE start_date <= ?1 AND end_date >= ?1", nativeQuery = true)
    int getPreviousBookingsCount(String startDate);

    /**
     * Retrieves the count of bookings that fall between the specified start and end dates.
     *
     * @param startDate The start date to check
     * @param endDate   The end date to check
     * @return The count of bookings between the dates
     */
    @Query(value = "SELECT COUNT(*) FROM bookings WHERE start_date > ?1 AND end_date < ?2", nativeQuery = true)
    int getBetweenBookingsCount(String startDate, String endDate);

    /**
     * Retrieves the count of future bookings that include the specified end date.
     *
     * @param endDate The end date to check
     * @return The count of future bookings
     */
    @Query(value = "SELECT COUNT(*) FROM bookings WHERE start_date <= ?1 AND end_date >= ?1", nativeQuery = true)
    int getFutureBookingsCount(String endDate);

    /**
     * Retrieves all bookings associated with the specified user.
     *
     * @param user The user to retrieve bookings for
     * @return The list of bookings associated with the user
     */
    List<Booking> findByUser(User user);

    /**
     * Retrieves all bookings associated with the specified venue.
     *
     * @param venue The venue to retrieve bookings for
     * @return The list of bookings associated with the venue
     */
    List<Booking> findByVenue(Venue venue);

    /**
     * Deletes all bookings associated with the specified venue.
     *
     * @param venue The venue to delete bookings for
     */
    void deleteBookingByVenue(Venue venue);
}
