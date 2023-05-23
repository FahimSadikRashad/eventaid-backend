package com.example.eventlybackend.evently.exception;

/**
 * The UserNotFoundException is an exception class that is thrown when a user with a specific ID is not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified user ID.
     *
     * @param id The ID of the user that was not found.
     */
    public UserNotFoundException(Long id) {
        super("Could not find the user with ID " + id);
    }
}

