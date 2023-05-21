package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Integer> {

    @Query(value = "SELECT COUNT(*) FROM bookings WHERE start_date <= ?1 AND end_date >= ?1", nativeQuery = true)
    int getPreviousBookingsCount(String startDate);

    @Query(value = "SELECT COUNT(*) FROM bookings WHERE start_date => ?1 AND end_date <= ?2", nativeQuery = true)
    int getBetweenBookingsCount(String startDate, String endDate);

    @Query(value = "SELECT COUNT(*) FROM bookings WHERE start_date <= ?1 AND end_date >= ?1", nativeQuery = true)
    int getFutureBookingsCount(String endDate);

    List<Booking> findByUser(User user);

    List<Booking> findByVenue(Venue venue);

    void deleteBookingByVenue(Venue venue);


}