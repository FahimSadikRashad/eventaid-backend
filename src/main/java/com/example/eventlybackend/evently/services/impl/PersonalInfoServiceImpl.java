package com.example.eventlybackend.evently.services.impl;

import com.example.eventlybackend.evently.exception.ResourceNotFoundException;
import com.example.eventlybackend.evently.model.PersonalInfo;
import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.payloads.PersonalInfoDto;
import com.example.eventlybackend.evently.repository.PersonalInfoRepo;
import com.example.eventlybackend.evently.repository.UserRepo;
import com.example.eventlybackend.evently.services.PersonalInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing personal information of a user.
 */
@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonalInfoRepo personalInfoRepo;

    /**
     * Updates the user profile with the provided personal information.
     *
     * @param personalInfoDto The DTO containing the personal information to update
     * @param uid             The ID of the user
     * @return The updated personal information DTO
     * @throws ResourceNotFoundException If the user with the given ID is not found
     */
    @Override
    public PersonalInfoDto updateUserProfile(PersonalInfoDto personalInfoDto, Integer uid) {
        User user = this.userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", uid));

        PersonalInfo personalInfo = this.personalInfoRepo.findByUser(user);
        if (personalInfo == null) {
            personalInfo = this.modelMapper.map(personalInfoDto, PersonalInfo.class);
            personalInfo.setUser(user);
        } else {
            personalInfo.setUser(user);
            personalInfo.setFirstname(personalInfoDto.getFirstname());
            personalInfo.setLastname(personalInfoDto.getLastname());
            personalInfo.setEmail(personalInfoDto.getEmail());
            personalInfo.setGender(personalInfoDto.getGender());
            personalInfo.setPhone(personalInfoDto.getPhone());
            personalInfo.setAddress(personalInfoDto.getAddress());
        }

        PersonalInfo savedInfo = this.personalInfoRepo.save(personalInfo);
        PersonalInfoDto personalInfoDto1 = this.modelMapper.map(savedInfo, PersonalInfoDto.class);
        return personalInfoDto1;
    }

    /**
     * Retrieves the user profile for the given user ID.
     *
     * @param uid The ID of the user
     * @return The user profile DTO if found, null otherwise
     * @throws ResourceNotFoundException If the user with the given ID is not found
     */
    @Override
    public PersonalInfoDto getUserProfile(Integer uid) {
        User user = this.userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", uid));

        PersonalInfo personalInfo = this.personalInfoRepo.findByUser(user);
        PersonalInfoDto personalInfoDto1 = null;
        if (personalInfo != null) {
            personalInfoDto1 = this.modelMapper.map(personalInfo, PersonalInfoDto.class);
        }
        return personalInfoDto1;
    }
}
