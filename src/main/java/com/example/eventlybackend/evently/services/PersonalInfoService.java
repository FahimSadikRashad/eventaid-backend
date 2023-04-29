package com.example.eventlybackend.evently.services;

import com.example.eventlybackend.evently.model.PersonalInfo;
import com.example.eventlybackend.evently.payloads.PersonalInfoDto;

public interface PersonalInfoService {
    PersonalInfoDto updateUserProfile(PersonalInfoDto personalInfoDto,Integer uid);
    PersonalInfoDto getUserProfile(Integer uid);
}
