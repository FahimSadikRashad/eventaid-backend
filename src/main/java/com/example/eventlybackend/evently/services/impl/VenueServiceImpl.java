package com.example.eventlybackend.evently.services.impl;

import com.example.eventlybackend.evently.exception.ResourceNotFoundException;
import com.example.eventlybackend.evently.model.Event;
import com.example.eventlybackend.evently.model.FoodorService;
import com.example.eventlybackend.evently.model.Venue;
import com.example.eventlybackend.evently.payloads.EventDto;
import com.example.eventlybackend.evently.payloads.FoodorServiceDto;
import com.example.eventlybackend.evently.payloads.VenueDto;
import com.example.eventlybackend.evently.payloads.VenueRequest;
import com.example.eventlybackend.evently.repository.EventRepo;
import com.example.eventlybackend.evently.repository.FoodorServiceRepo;
import com.example.eventlybackend.evently.repository.VenueRepo;
import com.example.eventlybackend.evently.services.VenueService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    private VenueRepo venueRepo;

    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private FoodorServiceRepo foodorServiceRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public VenueDto createVenue(VenueRequest venueRequest) {
        List<EventDto> eventDtoList=venueRequest.getEventDtoList();
        List<Event> events = eventDtoList.stream().map(eventDto -> this.modelMapper.map(eventDto,Event.class)).collect(Collectors.toList());

        List<FoodorServiceDto> foodorServiceDtos=venueRequest.getFoodorServicesList();
        List<FoodorService> foodorServices = foodorServiceDtos.stream().map(foodorServiceDto -> this.modelMapper.map(foodorServiceDto,FoodorService.class)).collect(Collectors.toList());


        VenueDto venueDto=venueRequest.getVenueDto();
        Venue venue=this.modelMapper.map(venueDto, Venue.class);

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
        List<VenueDto> venueDtos=venues.stream().map(venue -> this.modelMapper.map(venue,VenueDto.class)).collect(Collectors.toList());
        return venueDtos;
    }

    @Override
    public List<VenueDto> getVenueByName(String venueName) {
        List<Venue> venues=this.venueRepo.findByPlace(venueName);
        List<VenueDto> venueDtos=venues.stream().map(venue -> this.modelMapper.map(venue,VenueDto.class)).collect(Collectors.toList());
        return venueDtos;

    }
}
