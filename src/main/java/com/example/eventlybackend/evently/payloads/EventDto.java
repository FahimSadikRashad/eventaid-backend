package com.example.eventlybackend.evently.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class EventDto {

    private String eventName;

    private int eventCost;



}
