package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.PersonalInfo;
import com.example.eventlybackend.evently.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing PersonalInfo entities.
 */
public interface PersonalInfoRepo extends JpaRepository<PersonalInfo, Integer> {

    /**
     * Retrieves the PersonalInfo associated with the given User.
     *
     * @param user The User entity
     * @return The PersonalInfo associated with the User
     */
    PersonalInfo findByUser(User user);
}
