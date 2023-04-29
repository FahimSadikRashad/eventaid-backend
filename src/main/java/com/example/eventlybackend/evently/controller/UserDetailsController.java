package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.exception.ResourceNotFoundException;
import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.UserDetails;
import com.example.eventlybackend.evently.repository.UserDetailsRepo;
import com.example.eventlybackend.evently.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userDetails")
@CrossOrigin("http://localhost:3000")
public class UserDetailsController {

    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<UserDetails> getAllPersonalInformation() {
        return userDetailsRepo.findAll();
    }

    @GetMapping("/{id}")
    public UserDetails getPersonalInformationById(@PathVariable("id") int id) {
        return userDetailsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PersonalInformation", "id", id));
    }

    @PostMapping
    public UserDetails createPersonalInformation(@RequestBody UserDetails personalInformation) {
        return userDetailsRepo.save(personalInformation);
    }
//    firstName: '',
    // lastName: '',
    // email: '',
    // gender: '',
    // phone: '',
    // address: '',
    // picture: null,
    // userId: ''
    @PostMapping("/upload")
    public UserDetails uploadFile(@RequestParam("firstName") String firstName,
                                             @RequestParam("lastName") String lastName,
                                             @RequestParam("email") String email,
                                             @RequestParam("gender") String gender,
                                             @RequestParam("phone") String phone,
                                             @RequestParam("address") String address,
                                             @RequestParam("userId") int userId,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        // process the form data and file here
        // ...
        UserDetails personalInformation=new UserDetails();
        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));;
//        UserDetails personalInformation = userDetailsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PersonalInformation", "id", id));

        personalInformation.setUser(user);
        personalInformation.setFirstname(firstName);
        personalInformation.setLastname(lastName);
        personalInformation.setEmail(email);
        personalInformation.setGender(gender);
        personalInformation.setPhone(phone);
        personalInformation.setAddress(address);
        personalInformation.setPicture(file.getBytes());
        return userDetailsRepo.save(personalInformation);

//        return ResponseEntity.ok("File uploaded successfully");
    }

    @PutMapping("/{id}")
    public UserDetails updatePersonalInformation(@PathVariable("id") int id, @RequestBody UserDetails personalInformationDetails) {
        UserDetails personalInformation = userDetailsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PersonalInformation", "id", id));
        personalInformation.setUser(personalInformationDetails.getUser());
        personalInformation.setFirstname(personalInformationDetails.getFirstname());
        personalInformation.setLastname(personalInformationDetails.getLastname());
        personalInformation.setEmail(personalInformationDetails.getEmail());
        personalInformation.setGender(personalInformationDetails.getGender());
        personalInformation.setPhone(personalInformationDetails.getPhone());
        personalInformation.setAddress(personalInformationDetails.getAddress());
        personalInformation.setPicture(personalInformationDetails.getPicture());
        return userDetailsRepo.save(personalInformation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonalInformation(@PathVariable("id") int id) {
        UserDetails personalInformation = userDetailsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PersonalInformation", "id", id));
        userDetailsRepo.delete(personalInformation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserDetails>> getUserDetailsByUserId(@PathVariable int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            List<UserDetails> userDetails =this.userDetailsRepo.findByUser(user.get());

            if (userDetails!=null) {
                return ResponseEntity.ok().body(userDetails);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}