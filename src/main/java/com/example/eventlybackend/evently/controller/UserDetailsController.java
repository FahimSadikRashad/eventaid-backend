package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.exception.ResourceNotFoundException;
import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.UserDetails;
import com.example.eventlybackend.evently.repository.UserDetailsRepo;
import com.example.eventlybackend.evently.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found","Id",userId));
        List<UserDetails> personalInformations=this.userDetailsRepo.findByUser(user);

        UserDetails personalInformation;
        if(personalInformations.get(0)==null) personalInformation=new UserDetails();
        else  personalInformation=personalInformations.get(0);
//        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));;
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

    @GetMapping("/userPic/{userId}")
    public ResponseEntity<Resource> getUserProfilePictureByUserId(@PathVariable int userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            List<UserDetails> userDetails =this.userDetailsRepo.findByUser(user.get());
            UserDetails userDetail=userDetails.get(0);
            if (userDetail != null && userDetail.getPicture() != null) {
                System.out.println("Profile is send");
                byte[] profilePicture = userDetail.getPicture();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type based on your file format

                // Create a Resource object with the profile picture data
                Resource resource = new ByteArrayResource(profilePicture);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}