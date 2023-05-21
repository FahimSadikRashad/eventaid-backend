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


    @Override
    public VenueDto createVenue(VenueRequest venueRequest) {
        List<EventDto> eventDtoList=venueRequest.getEventDtoList();
        List<Event> events = eventDtoList.stream().map(eventDto -> this.modelMapper.map(eventDto,Event.class)).collect(Collectors.toList());

        List<FoodorServiceDto> foodorServiceDtos=venueRequest.getFoodorServicesList();
        List<FoodorService> foodorServices = foodorServiceDtos.stream().map(foodorServiceDto -> this.modelMapper.map(foodorServiceDto,FoodorService.class)).collect(Collectors.toList());


        VenueDtoSave venueDto=venueRequest.getVenueDto();
        Venue venue=this.modelMapper.map(venueDto, Venue.class);
        int uid=venueRequest.getUserId();
        User user=this.userRepo.findById(uid).orElseThrow(()->new ResourceNotFoundException("User","id",uid));
        venue.setUser(user);

//        venue.setEvents(events);
//        venue.setFoods(foodorServices);
        Venue savedVenue=this.venueRepo.save(venue);
        for(Event event:events){
            event.setVenue(savedVenue);
            this.eventRepo.save(event);
        }
        for(FoodorService foodorService:foodorServices){
            foodorService.setVenue(savedVenue);
            this.foodorServiceRepo.save(foodorService);
        }

        return this.modelMapper.map(savedVenue,VenueDto.class);
    }

    @Override
    public VenueDto getVenueById(Integer vid) {
        Venue venue=this.venueRepo.findById(vid).orElseThrow(()->new ResourceNotFoundException("Venue","Id",vid));
        return this.modelMapper.map(venue,VenueDto.class);
    }

    @Override
    public List<VenueDto> getAllVenues() {
        List<Venue> venues=this.venueRepo.findAll();
//        List<Booking> bookings=this.bookingRepo.findByVenue(venues.get(0));
//        System.out.println(bookings.get(0).getPlace());
        List<VenueDto> venueDtos=venues.stream().map(venue -> this.modelMapper.map(venue,VenueDto.class)).collect(Collectors.toList());
        return venueDtos;
    }

    @Override
    public List<VenueDto> getVenueByName(String venueName) {
        List<Venue> venues=this.venueRepo.findByPlace(venueName);
        List<VenueDto> venueDtos=venues.stream().map(venue -> this.modelMapper.map(venue,VenueDto.class)).collect(Collectors.toList());
        return venueDtos;

    }
    @Override
    public List<VenueDto> getVenuesByUsername(String username) {
        User user = this.userRepo.findUserByName(username);
        List<Venue> venues=this.venueRepo.findByUser(user);
        if (user == null) {
            return Collections.emptyList();
        } else {
            List<VenueDto> venueDtos = new ArrayList<>();
            for (Venue venue : venues) {
                venueDtos.add(this.modelMapper.map(venue,VenueDto.class));
            }
            return venueDtos;
        }
    }

    @Override
    @Transactional
    public void deleteVenue(int vid) {
        Venue venue = this.venueRepo.findById(vid).orElse(null);
        List<Event> events=venue.getEvents();
        List<FoodorService> foodorServices=venue.getFoods();

        this.bookingRepo.deleteBookingByVenue(venue);
        if(events!=null){
            for(int i=0;i<events.size();i++){
                this.eventRepo.delete(events.get(i));
            }
        }

        if(foodorServices!=null){
            for(int i=0;i<foodorServices.size();i++){
                this.foodorServiceRepo.delete(foodorServices.get(i));
            }
        }

        if (venue != null) {
            this.venueRepo.delete(venue);
        }
    }

    @Override
    public List<String> getAllPlaces() {

        return this.venueRepo.findDistinctPlaces();
    }

    @Override
    public Boolean BookingCheck(BookingRequest bookingRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int cnt1=this.bookingRepo.getPreviousBookingsCount(bookingRequest.getStartDate().format(formatter));
        int cnt2=this.bookingRepo.getBetweenBookingsCount(bookingRequest.getStartDate().format(formatter),bookingRequest.getEndDate().format(formatter));
        int cnt3=this.bookingRepo.getFutureBookingsCount(bookingRequest.getEndDate().format(formatter));

        if((cnt1+cnt2+cnt3)>0) return false;
        else return  true;


    }

    @Override
    public Booking createBooking(BookingRequest bookingRequest) {
        User user=this.userRepo.findById(bookingRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found","Id", bookingRequest.getUserId()));
        Venue venue = this.venueRepo.findById(bookingRequest.getVenueId()).orElseThrow(() -> new ResourceNotFoundException("Venue not found","Id", bookingRequest.getVenueId()));
        Event event = this.eventRepo.findById(bookingRequest.getEventId()).orElseThrow(() -> new ResourceNotFoundException("Event not found","Id", bookingRequest.getEventId()));
//        List<FoodorService> foods = this.foodorServiceRepo.findAllById(bookingRequest.getFoodIds()).stream().toList();
        List<FoodorService> foods=new ArrayList<>();
        Set<FoodorService> newFoods=new HashSet<>();
        for(Integer id:bookingRequest.getServiceIds()){

            FoodorService foodorService=this.foodorServiceRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food not found","Id",id));
            System.out.println(id+"   "+foodorService.getServiceName());

            foods.add(foodorService);
            newFoods.add(foodorService);
        }
        for(Integer id:bookingRequest.getFoodIds()){

            FoodorService foodorService=this.foodorServiceRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food not found","Id",id));
            System.out.println(id+"   "+foodorService.getServiceName());

            foods.add(foodorService);
            newFoods.add(foodorService);
        }
//        List<FoodorService> services = this.foodorServiceRepo.findAllById(bookingRequest.getServiceIds());

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
        System.out.println("Prinitng the status:"+booking.getStatus());
        booking.setStatus(bookingRequest.getStatus());

//        foods.stream().map((food)->{
//
//            return newFoods.add(food);
//        });
        booking.setFood(newFoods);
//        booking.setServices(services);

        Booking savedBooking = this.bookingRepo.save(booking);
        return savedBooking;
    }

    @Override
    public List<Booking> getBookingByUser(int uid) {
        User user=this.userRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("User not found","Id",uid));
        List<Booking> bookings=this.bookingRepo.findByUser(user);
        return bookings;
    }

    @Override
    public Booking getBookingById(int bid) {

        return this.bookingRepo.findById(bid).orElseThrow(() -> new ResourceNotFoundException("booking not found","Id",bid));
    }

    @Override
    public void deleteBookingById(int bid) {
        this.bookingRepo.deleteById(bid);
    }

    @Override
    public List<Booking> getBookingByUserName(String username) {

        User user=this.userRepo.findUserByName(username);
        System.out.println(user.getName());

        List<Booking> bookings=this.bookingRepo.findByUser(user);

        return bookings;
    }

    @Override
    public List<Booking> getBookingByVenueId(int vid) {

        Venue venue=this.venueRepo.findById(vid).orElseThrow(() -> new ResourceNotFoundException("Venue not found","Id",vid));
//        System.out.println(user.getName());

        List<Booking> bookings=this.bookingRepo.findByVenue(venue);

        return bookings;
    }

    @Override
    public VenueDto updateVenue(VenueRequest venueRequest,int vid) {

        Venue venue = this.venueRepo.findById(vid).orElse(null);
        List<EventDto> eventDtoList=venueRequest.getEventDtoList();
        List<Event> newEvents = eventDtoList.stream().map(eventDto -> this.modelMapper.map(eventDto,Event.class)).collect(Collectors.toList());

        List<FoodorServiceDto> foodorServiceDtos=venueRequest.getFoodorServicesList();
        List<FoodorService> newFoodorServices = foodorServiceDtos.stream().map(foodorServiceDto -> this.modelMapper.map(foodorServiceDto,FoodorService.class)).collect(Collectors.toList());


        VenueDtoSave venueDto=venueRequest.getVenueDto();
        Venue newVenue=this.modelMapper.map(venueDto, Venue.class);
        venue.setVenueName(newVenue.getVenueName());
        venue.setPlace(newVenue.getPlace());
        venue.setContact(newVenue.getContact());





        Venue savedVenue=this.venueRepo.save(venue);
        for(Event event:newEvents){
            Event prevEvent=this.eventRepo.findById(event.getId()).orElse(null);
            if(prevEvent!=null){
                prevEvent.setEventName(event.getEventName());
                prevEvent.setEventCost(event.getEventCost());

            }
            else{
                prevEvent =new Event();
                prevEvent.setEventName(event.getEventName());
                prevEvent.setEventCost(event.getEventCost());
                prevEvent.setVenue(venue);
            }
            this.eventRepo.save(prevEvent);
        }
        for(FoodorService foodorService:newFoodorServices){
            FoodorService prevFood=this.foodorServiceRepo.findById(foodorService.getId()).orElse(null);
            if(prevFood!=null){
                prevFood.setServiceName(foodorService.getServiceName());
                prevFood.setServiceCost(foodorService.getServiceCost());
                prevFood.setWhat(foodorService.getWhat());

            }
            else{
                prevFood =new FoodorService();
                prevFood.setServiceName(foodorService.getServiceName());
                prevFood.setServiceCost(prevFood.getServiceCost());
                prevFood.setWhat(foodorService.getWhat());
                prevFood.setVenue(venue);
            }
            this.foodorServiceRepo.save(prevFood);
        }

        return this.modelMapper.map(savedVenue,VenueDto.class);
    }

    @Override
    public Booking updateBooking(BookingRequest bookingRequest, int bookId) {
        Booking booking=this.bookingRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Booking  not found","Id",bookId));
        booking.setStatus(bookingRequest.getStatus());
        Booking updatedBooking=this.bookingRepo.save(booking);

        return  updatedBooking;
    }
}
