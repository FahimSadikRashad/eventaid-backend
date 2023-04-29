package com.example.eventlybackend.evently.services;


import com.example.eventlybackend.evently.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);


    void registerUser(UserDto userDto);
    UserDto getUserByUsername(String username);

}
