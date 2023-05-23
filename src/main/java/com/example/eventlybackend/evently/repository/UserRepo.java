package com.example.eventlybackend.evently.repository;


import com.example.eventlybackend.evently.model.User;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepo extends JpaRepository<User, Integer> {

    /**
     * Retrieves a User entity by name.
     *
     * @param userName The name of the user
     * @return The User entity matching the name
     */
    User findUserByName(String userName);
}
