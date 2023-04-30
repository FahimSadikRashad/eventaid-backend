package com.example.eventlybackend.evently.controller;


import com.example.eventlybackend.evently.payloads.ApiResponse;
import com.example.eventlybackend.evently.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.eventlybackend.evently.payloads.UserDto;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
        UserDto updatedUser = this.userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }

    // GET - user get
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // GET - user get
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<UserDto> getNewUser(@PathVariable String userName) {
        return ResponseEntity.ok(this.userService.getUserByUsername(userName));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDto user) {
        System.out.println(user.getName());
        if(user.getName()==null & user.getPassword()==null &  user.getRole()==null & user.getEmail()==null){
            return new  ResponseEntity<ApiResponse>(new ApiResponse("Can't process null value",false),HttpStatus.OK);
        }
        if (userService.getUserByUsername(user.getName()) != null) {
            return new  ResponseEntity<ApiResponse>(new ApiResponse("Username already exists",false),HttpStatus.OK);
        }
        userService.registerUser(user);
        return new  ResponseEntity<ApiResponse>(new ApiResponse("User registered successfully",true),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserDto user) {
        UserDto existingUser = userService.getUserByUsername(user.getName());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return new  ResponseEntity<ApiResponse>(new ApiResponse("Invalid username or password",false),HttpStatus.OK);
        }
        return new  ResponseEntity<ApiResponse>(new ApiResponse("Log in Successful",true,existingUser.getId()),HttpStatus.OK);
    }
}
