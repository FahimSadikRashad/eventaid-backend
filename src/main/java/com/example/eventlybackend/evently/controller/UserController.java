package com.example.eventlybackend.evently.controller;


import com.example.eventlybackend.evently.payloads.ApiResponse;
import com.example.eventlybackend.evently.payloads.LoginDto;
import com.example.eventlybackend.evently.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.eventlybackend.evently.payloads.UserDto;

import java.util.List;

/**

 The {@code UserController} class is a REST controller that handles

 requests related to user management.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     Creates a new user.
     @param userDto The user information to create.
     @return A ResponseEntity containing the created UserDto.
     */
    @PostMapping("/add")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    /**
     Updates an existing user.
     @param userDto The updated user information.
     @param uid The ID of the user to update.
     @return A ResponseEntity containing the updated UserDto.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
        UserDto updatedUser = this.userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);
    }

    /**

     Deletes a user.
     @param uid The ID of the user to delete.
     @return A ResponseEntity with a success message.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }

    /**
     Retrieves all users.
     @return A ResponseEntity containing a list of UserDto.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // GET - user get
    /**

     Retrieves a single user by ID.
     @param userId The ID of the user to retrieve.
     @return A ResponseEntity containing the UserDto.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    /**

     Retrieves a new user by username.
     @param userName The username of the user to retrieve.
     @return A ResponseEntity containing the UserDto.
     */
    @GetMapping("/username/{userName}")
    public ResponseEntity<UserDto> getNewUser(@PathVariable String userName) {
        return ResponseEntity.ok(this.userService.getUserByUsername(userName));
    }

    /**

     Registers a new user.

     @param user The user information to register.

     @return A ResponseEntity with a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody LoginDto user) {
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

    /**

     Logs in a user.
     @param user The login information.
     @return A ResponseEntity with a success message and user ID.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginDto user) {
        LoginDto existingUser = userService.getUserByUsernameLogin(user.getName());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return new  ResponseEntity<ApiResponse>(new ApiResponse("Invalid username or password",false),HttpStatus.OK);
        }
        return new  ResponseEntity<ApiResponse>(new ApiResponse("Log in Successful",true,existingUser.getId()),HttpStatus.OK);
    }
}
