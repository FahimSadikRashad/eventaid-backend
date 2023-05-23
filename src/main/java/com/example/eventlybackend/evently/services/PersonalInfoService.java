package com.example.eventlybackend.evently.services;

import com.example.eventlybackend.evently.model.PersonalInfo;
import com.example.eventlybackend.evently.payloads.PersonalInfoDto;

public interface PersonalInfoService {

    /**
     * Updates the user profile for the specified user ID with the provided personal information.
     *
     * @param personalInfoDto The PersonalInfoDto object containing the updated personal information.
     * @param uid             The ID of the user whose profile needs to be updated.
     * @return The updated PersonalInfoDto object representing the user's updated profile.
     */
    PersonalInfoDto updateUserProfile(PersonalInfoDto personalInfoDto, Integer uid);

    /**
     * Retrieves the user profile for the specified user ID.
     *
     * @param uid The ID of the user whose profile needs to be retrieved.
     * @return The PersonalInfoDto object representing the user's profile.
     */
    PersonalInfoDto getUserProfile(Integer uid);
}
