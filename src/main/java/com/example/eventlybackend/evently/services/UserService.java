package com.example.eventlybackend.evently.services;


import com.example.eventlybackend.evently.payloads.LoginDto;
import com.example.eventlybackend.evently.payloads.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Creates a new user with the provided user information.
     *
     * @param user The UserDto object containing the user information.
     * @return The UserDto object representing the created user.
     */
    UserDto createUser(UserDto user);

    /**
     * Updates the user with the provided user information for the specified user ID.
     *
     * @param user    The UserDto object containing the updated user information.
     * @param userId  The ID of the user to be updated.
     * @return The UserDto object representing the updated user.
     */
    UserDto updateUser(UserDto user, Integer userId);

    /**
     * Retrieves the user by the specified user ID.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The UserDto object representing the retrieved user.
     */
    UserDto getUserById(Integer userId);

    /**
     * Retrieves a list of all users.
     *
     * @return A list of UserDto objects representing all users.
     */
    List<UserDto> getAllUsers();

    /**
     * Deletes the user with the specified user ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    void deleteUser(Integer userId);

    /**
     * Registers a new user with the provided login information.
     *
     * @param userDto The LoginDto object containing the user's login information.
     */
    void registerUser(LoginDto userDto);

    /**
     * Retrieves the user by the specified username.
     *
     * @param username The username of the user to be retrieved.
     * @return The UserDto object representing the retrieved user.
     */
    UserDto getUserByUsername(String username);

    /**
     * Retrieves the login information for the user with the specified username.
     *
     * @param username The username of the user to retrieve the login information.
     * @return The LoginDto object representing the user's login information.
     */
    LoginDto getUserByUsernameLogin(String username);
}
