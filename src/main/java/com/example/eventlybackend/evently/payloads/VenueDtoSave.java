package com.example.eventlybackend.evently.payloads;

import com.example.eventlybackend.evently.model.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The VenueDtoSave class represents the data transfer object for saving venue information.
 */
@Getter
@Setter
@NoArgsConstructor
public class VenueDtoSave {


    private  int id;
    private String venueName;

    private String place;

    private String contact;



}
