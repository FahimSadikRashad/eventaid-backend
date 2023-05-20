package com.example.eventlybackend.evently.payloads;

import com.example.eventlybackend.evently.model.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VenueDtoSave {


    private  int id;
    private String venueName;

    private String place;

    private String contact;



}
