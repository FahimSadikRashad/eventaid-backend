package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing UserDetails entities.
 */
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {

    /**
     * Retrieves the UserDetails associated with the given User.
     *
     * @param user The User entity
     * @return The list of UserDetails associated with the User
     */
    List<UserDetails> findByUser(User user);
}
