package com.example.eventlybackend.evently.services.impl;

import com.example.eventlybackend.evently.exception.ResourceNotFoundException;
import com.example.eventlybackend.evently.model.Booking;
import com.example.eventlybackend.evently.payloads.BookingDto;
import com.example.eventlybackend.evently.payloads.LoginDto;
import com.example.eventlybackend.evently.repository.BookingRepo;
import com.example.eventlybackend.evently.services.UserService;
import com.example.eventlybackend.evently.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.eventlybackend.evently.payloads.UserDto;
import com.example.eventlybackend.evently.repository.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing users.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new user with the provided user information.
     *
     * @param userDto The DTO containing the user information
     * @return The created user DTO
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    /**
     * Updates the user with the provided user information.
     *
     * @param userDto The DTO containing the updated user information
     * @param userId  The ID of the user to update
     * @return The updated user DTO
     * @throws ResourceNotFoundException If the user with the given ID is not found
     */
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    /**
     * Retrieves the user with the given user ID.
     *
     * @param userId The ID of the user
     * @return The user DTO if found
     * @throws ResourceNotFoundException If the user with the given ID is not found
     */
    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        return this.userToDto(user);
    }

    /**
     * Retrieves all users.
     *
     * @return The list of user DTOs
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    /**
     * Deletes the user with the given user ID.
     *
     * @param userId The ID of the user to delete
     * @throws ResourceNotFoundException If the user with the given ID is not found
     */
    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    /**
     * Registers a new user with the provided login information.
     *
     * @param userDto The DTO containing the login information
     */
    @Override
    public void registerUser(LoginDto userDto) {
        User user=this.modelMapper.map(userDto, User.class);
        this.userRepo.save(user);
    }

    /**
     * Retrieves the user DTO with the given username.
     *
     * @param username The username of the user
     * @return The user DTO if found, or null if not found
     */
    @Override
    public UserDto getUserByUsername(String username) {
        User user=this.userRepo.findUserByName(username);

//        List<Booking> bookings=this.bookingRepo.findByUser(user);
        if(user==null) return null;
        else {
            UserDto userDto =this.userToDto(user);

            return userDto;
        }

    }

    /**
     * Retrieves the login DTO with the given username.
     *
     * @param username The username of the user
     * @return The login DTO if found, or null if not found
     */
    @Override
    public LoginDto getUserByUsernameLogin(String username) {
        User user=this.userRepo.findUserByName(username);


        if(user==null) return null;
        else {
            LoginDto userDto =this.modelMapper.map(user, LoginDto.class);

            return userDto;
        }

    }

    /**
     * Converts a UserDto object to a User object.
     *
     * @param userDto The UserDto object to convert
     * @return The converted User object
     */
    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }
    /**
     * Converts a User object to a UserDto object.
     *
     * @param user The User object to convert
     * @return The converted UserDto object
     */
    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
