package com.example.eventlybackend.evently.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodorServiceDto {


    private String serviceName;

    private int serviceCost;

}
