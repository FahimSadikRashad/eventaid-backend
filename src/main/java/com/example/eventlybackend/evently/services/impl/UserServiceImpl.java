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


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

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

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public void registerUser(LoginDto userDto) {
        User user=this.modelMapper.map(userDto, User.class);
        this.userRepo.save(user);
    }

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

    @Override
    public LoginDto getUserByUsernameLogin(String username) {
        User user=this.userRepo.findUserByName(username);


        if(user==null) return null;
        else {
            LoginDto userDto =this.modelMapper.map(user, LoginDto.class);

            return userDto;
        }

    }

    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
