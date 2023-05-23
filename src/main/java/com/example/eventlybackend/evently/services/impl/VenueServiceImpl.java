package com.example.eventlybackend.evently.services.impl;

import com.example.eventlybackend.evently.exception.ResourceNotFoundException;
import com.example.eventlybackend.evently.model.*;
import com.example.eventlybackend.evently.payloads.*;
import com.example.eventlybackend.evently.repository.*;
import com.example.eventlybackend.evently.services.VenueService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the VenueService interface that provides CRUD operations for venues.
 */
@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepo venueRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private FoodorServiceRepo foodorServiceRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new venue with the provided venue request.
     *
     * @param venueRequest The venue request containing the venue details
     * @return The created venue as VenueDto
     */
    @Override
    public VenueDto createVenue(VenueRequest venueRequest) {
        // Convert event and foodorService DTOs to objects
        List<EventDto> eventDtoList = venueRequest.getEventDtoList();
        List<Event> events = eventDtoList.stream()
                .map(eventDto -> this.modelMapper.map(eventDto, Event.class))
                .collect(Collectors.toList());

        List<FoodorServiceDto> foodorServiceDtos = venueRequest.getFoodorServicesList();
        List<FoodorService> foodorServices = foodorServiceDtos.stream()
                .map(foodorServiceDto -> this.modelMapper.map(foodorServiceDto, FoodorService.class))
                .collect(Collectors.toList());

        // Convert venue DTO to object
        VenueDtoSave venueDto = venueRequest.getVenueDto();
        Venue venue = this.modelMapper.map(venueDto, Venue.class);

        // Find and set the user for the venue
        int uid = venueRequest.getUserId();
        User user = this.userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", uid));
        venue.setUser(user);

        // Save the venue
        Venue savedVenue = this.venueRepo.save(venue);

        // Set the venue for events and foodorServices, and save them
        for (Event event : events) {
            event.setVenue(savedVenue);
            this.eventRepo.save(event);
        }

        for (FoodorService foodorService : foodorServices) {
            foodorService.setVenue(savedVenue);
            this.foodorServiceRepo.save(foodorService);
        }

        return this.modelMapper.map(savedVenue, VenueDto.class);
    }

    /**
     * Retrieves a venue by its ID.
     *
     * @param vid The ID of the venue
     * @return The retrieved venue as VenueDto
     * @throws ResourceNotFoundException If the venue is not found
     */
    @Override
    public VenueDto getVenueById(Integer vid) {
        Venue venue = this.venueRepo.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", "Id", vid));
        return this.modelMapper.map(venue, VenueDto.class);
    }

    /**
     * Retrieves all venues.
     *
     * @return A list of all venues as VenueDto
     */
    @Override
    public List<VenueDto> getAllVenues() {
        List<Venue> venues = this.venueRepo.findAll();
        List<VenueDto> venueDtos = venues.stream()
                .map(venue -> this.modelMapper.map(venue, VenueDto.class))
                .collect(Collectors.toList());
        return venueDtos;
    }

    /**
     * Retrieves venues by their name.
     *
     * @param venueName The name of the venue
     * @return A list of venues with the specified name as VenueDto
     */
    @Override
    public List<VenueDto> getVenueByName(String venueName) {
        List<Venue> venues = this.venueRepo.findByPlace(venueName);
        List<VenueDto> venueDtos = venues.stream()
                .map(venue -> this.modelMapper.map(venue, VenueDto.class))
                .collect(Collectors.toList());
        return venueDtos;
    }

    /**
     * Retrieves venues by the username of the associated user.
     *
     * @param username The username of the associated user
     * @return A list of venues associated with the specified username as VenueDto
     */
    @Override
    public List<VenueDto> getVenuesByUsername(String username) {
        User user = this.userRepo.findUserByName(username);
        List<Venue> venues = this.venueRepo.findByUser(user);
        if (user == null) {
            return Collections.emptyList();
        } else {
            List<VenueDto> venueDtos = new ArrayList<>();
            for (Venue venue : venues) {
                venueDtos.add(this.modelMapper.map(venue, VenueDto.class));
            }
            return venueDtos;
        }
    }

    /**
     * Deletes a venue by its ID.
     *
     * @param vid The ID of the venue to delete
     */
    @Override
    @Transactional
    public void deleteVenue(int vid) {
        Venue venue = this.venueRepo.findById(vid).orElse(null);
        List<Event> events = venue.getEvents();
        List<FoodorService> foodorServices = venue.getFoods();

        // Delete bookings related to the venue
        this.bookingRepo.deleteBookingByVenue(venue);

        // Delete events related to the venue
        if (events != null) {
            for (Event event : events) {
                this.eventRepo.delete(event);
            }
        }

        // Delete foodorServices related to the venue
        if (foodorServices != null) {
            for (FoodorService foodorService : foodorServices) {
                this.foodorServiceRepo.delete(foodorService);
            }
        }

        // Delete the venue itself
        if (venue != null) {
            this.venueRepo.delete(venue);
        }
    }

    /**
     * Retrieves all unique places from the venues.
     *
     * @return A list of all unique places
     */
    @Override
    public List<String> getAllPlaces() {
        return this.venueRepo.findDistinctPlaces();
    }

    /**
     * Checks if a booking can be made based on the provided booking request.
     *
     * @param bookingRequest The booking request
     * @return true if the booking can be made, false otherwise
     */
    @Override
    public Boolean BookingCheck(BookingRequest bookingRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int cnt1 = this.bookingRepo.getPreviousBookingsCount(bookingRequest.getStartDate().format(formatter));
        int cnt2 = this.bookingRepo.getBetweenBookingsCount(
                bookingRequest.getStartDate().format(formatter),
                bookingRequest.getEndDate().format(formatter));
        int cnt3 = this.bookingRepo.getFutureBookingsCount(bookingRequest.getEndDate().format(formatter));

        if ((cnt1 + cnt2 + cnt3) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates a new booking with the provided booking request.
     *
     * @param bookingRequest The booking request containing the booking details
     * @return The created booking
     * @throws ResourceNotFoundException If the associated user, venue, or event is not found
     */
    @Override
    public Booking createBooking(BookingRequest bookingRequest) {
        User user = this.userRepo.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found", "Id", bookingRequest.getUserId()));
        Venue venue = this.venueRepo.findById(bookingRequest.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found", "Id", bookingRequest.getVenueId()));
        Event event = this.eventRepo.findById(bookingRequest.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found", "Id", bookingRequest.getEventId()));

        List<FoodorService> foods = new ArrayList<>();
        Set<FoodorService> newFoods = new HashSet<>();

        for (Integer id : bookingRequest.getServiceIds()) {
            FoodorService foodorService = this.foodorServiceRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Food not found", "Id", id));
            foods.add(foodorService);
            newFoods.add(foodorService);
        }

        for (Integer id : bookingRequest.getFoodIds()) {
            FoodorService foodorService = this.foodorServiceRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Food not found", "Id", id));
            foods.add(foodorService);
            newFoods.add(foodorService);
        }

        Booking booking = new Booking();
        booking.setPlace(bookingRequest.getPlace());
        booking.setGuests(bookingRequest.getGuests());
        booking.setEventCost(bookingRequest.getEventCost());
        booking.setFoodCost(bookingRequest.getFoodCost());
        booking.setServiceCost(bookingRequest.getServiceCost());
        booking.setTotalCost(bookingRequest.getTotalCost());
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setUser(user);
        booking.setVenue(venue);
        booking.setEvent(event);
        booking.setStatus(bookingRequest.getStatus());
        booking.setFood(newFoods);

        Booking savedBooking = this.bookingRepo.save(booking);
        return savedBooking;
    }

    /**
     * Retrieves bookings associated with the user ID.
     *
     * @param uid The user ID
     * @return A list of bookings associated with the specified user ID
     */
    @Override
    public List<Booking> getBookingByUser(int uid) {
        User user = this.userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", "Id", uid));
        return this.bookingRepo.findByUser(user);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param bid The ID of the booking
     * @return The booking with the specified ID
     * @throws ResourceNotFoundException If the booking is not found
     */
    @Override
    public Booking getBookingById(int bid) {
        return this.bookingRepo.findById(bid)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found", "Id", bid));
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param bid The ID of the booking to delete
     */
    @Override
    public void deleteBookingById(int bid) {
        this.bookingRepo.deleteById(bid);
    }

    /**
     * Retrieves bookings associated with the username of the user.
     *
     * @param username The username of the user
     * @return A list of bookings associated with the specified username
     */
    @Override
    public List<Booking> getBookingByUserName(String username) {
        User user = this.userRepo.findUserByName(username);
        List<Booking> bookings = this.bookingRepo.findByUser(user);
        return bookings;
    }

    /**
     * Retrieves bookings associated with the venue ID.
     *
     * @param vid The venue ID
     * @return A list of bookings associated with the specified venue ID
     */
    @Override
    public List<Booking> getBookingByVenueId(int vid) {

        Venue venue=this.venueRepo.findById(vid).orElseThrow(() -> new ResourceNotFoundException("Venue not found","Id",vid));
//        System.out.println(user.getName());

        List<Booking> bookings=this.bookingRepo.findByVenue(venue);

        return bookings;
    }

    /**
     * Updates a venue in the system based on the provided venue request and venue ID.
     *
     * @param venueRequest The venue request containing the updated venue information.
     * @param vid          The ID of the venue to be updated.
     * @return The updated VenueDto object representing the updated venue.
     */
    @Override
    public VenueDto updateVenue(VenueRequest venueRequest, int vid) {
        // Retrieve the existing venue from the repository
        Venue venue = this.venueRepo.findById(vid).orElse(null);

        // Map the EventDto objects from the venue request to Event objects
        List<EventDto> eventDtoList = venueRequest.getEventDtoList();
        List<Event> newEvents = eventDtoList.stream()
                .map(eventDto -> this.modelMapper.map(eventDto, Event.class))
                .collect(Collectors.toList());

        // Map the FoodorServiceDto objects from the venue request to FoodorService objects
        List<FoodorServiceDto> foodorServiceDtos = venueRequest.getFoodorServicesList();
        List<FoodorService> newFoodorServices = foodorServiceDtos.stream()
                .map(foodorServiceDto -> this.modelMapper.map(foodorServiceDto, FoodorService.class))
                .collect(Collectors.toList());

        // Map the VenueDtoSave object from the venue request to a new Venue object
        VenueDtoSave venueDto = venueRequest.getVenueDto();
        Venue newVenue = this.modelMapper.map(venueDto, Venue.class);

        // Update the existing venue with the new venue details
        venue.setVenueName(newVenue.getVenueName());
        venue.setPlace(newVenue.getPlace());
        venue.setContact(newVenue.getContact());

        // Save the updated venue in the repository
        Venue savedVenue = this.venueRepo.save(venue);

        // Update or create new events associated with the venue
        for (Event event : newEvents) {
            Event prevEvent = this.eventRepo.findById(event.getId()).orElse(null);
            if (prevEvent != null) {
                prevEvent.setEventName(event.getEventName());
                prevEvent.setEventCost(event.getEventCost());
            } else {
                prevEvent = new Event();
                prevEvent.setEventName(event.getEventName());
                prevEvent.setEventCost(event.getEventCost());
                prevEvent.setVenue(venue);
            }
            this.eventRepo.save(prevEvent);
        }

        // Update or create new food or service items associated with the venue
        for (FoodorService foodorService : newFoodorServices) {
            FoodorService prevFood = this.foodorServiceRepo.findById(foodorService.getId()).orElse(null);
            if (prevFood != null) {
                prevFood.setServiceName(foodorService.getServiceName());
                prevFood.setServiceCost(foodorService.getServiceCost());
                prevFood.setWhat(foodorService.getWhat());
            } else {
                prevFood = new FoodorService();
                prevFood.setServiceName(foodorService.getServiceName());
                prevFood.setServiceCost(foodorService.getServiceCost());
                prevFood.setWhat(foodorService.getWhat());
                prevFood.setVenue(venue);
            }
            this.foodorServiceRepo.save(prevFood);
        }

        // Map the updated venue to a VenueDto object
        return this.modelMapper.map(savedVenue, VenueDto.class);
    }

    /**
     * Updates a booking in the system based on the provided booking request and booking ID.
     *
     * @param bookingRequest The booking request containing the updated booking information.
     * @param bookId         The ID of the booking to be updated.
     * @return The updated Booking object representing the updated booking.
     * @throws ResourceNotFoundException if the booking with the specified ID is not found.
     */
    @Override
    public Booking updateBooking(BookingRequest bookingRequest, int bookId) throws ResourceNotFoundException {
        // Retrieve the existing booking from the repository or throw an exception if not found
        Booking booking = this.bookingRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found", "Id", bookId));

        // Update the booking status with the new status from the booking request
        booking.setStatus(bookingRequest.getStatus());

        // Save the updated booking in the repository
        Booking updatedBooking = this.bookingRepo.save(booking);

        return updatedBooking;
    }



}
