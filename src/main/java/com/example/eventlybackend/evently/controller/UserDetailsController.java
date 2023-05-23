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


/**

 The {@code UserDetailsController} class is a REST controller that handles

 requests related to user details management.
 */
@RestController
    @RequestMapping("/api/userDetails")
@CrossOrigin("http://localhost:3000")
public class UserDetailsController {

    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private UserRepo userRepo;

    /**

     Retrieves all personal information records.
     @return A list of UserDetails.
     */
    @GetMapping
    public List<UserDetails> getAllPersonalInformation() { return userDetailsRepo.findAll();}
    /**

     Retrieves a specific personal information record by ID.
     @param id The ID of the personal information record to retrieve.
     @return The UserDetails with the specified ID.
     @throws ResourceNotFoundException if the personal information record is not found.
     */
    @GetMapping("/{id}")
    public UserDetails getPersonalInformationById(@PathVariable("id") int id) {
        return userDetailsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PersonalInformation", "id", id));
    }

    /**

     Creates a new personal information record.
     @param personalInformation The UserDetails object to create.
     @return The created UserDetails object.
     */
    @PostMapping
    public UserDetails createPersonalInformation(@RequestBody UserDetails personalInformation) {
        return userDetailsRepo.save(personalInformation);
    }
    /**

     Uploads a file and updates the personal information record.
     @param firstName The first name of the user.
     @param lastName The last name of the user.
     @param email The email of the user.
     @param gender The gender of the user.
     @param phone The phone number of the user.
     @param address The address of the user.
     @param userId The ID of the user.
     @param file The file to upload.
     @return The updated UserDetails object.
     @throws IOException if there is an error processing the file.
     */
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

    /**

     Updates an existing personal information record.
     @param id The ID of the personal information record to update.
     @param personalInformationDetails The updated UserDetails object.
     @return The updated UserDetails object.
     @throws ResourceNotFoundException if the personal information record is not found.
     */
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

    /**

     Deletes a personal information record.
     @param id The ID of the personal information record to delete.
     @return A ResponseEntity with no content.
     @throws ResourceNotFoundException if the personal information record is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonalInformation(@PathVariable("id") int id) {
        UserDetails personalInformation = userDetailsRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PersonalInformation", "id", id));
        userDetailsRepo.delete(personalInformation);
        return ResponseEntity.ok().build();
    }

    /**

     Retrieves all personal information records associated with a user.
     @param userId The ID of the user.
     @return A ResponseEntity containing a list of UserDetails.
     */
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

    /**

     Retrieves the profile picture of a user.

     @param userId The ID of the user.

     @return A ResponseEntity containing the user's profile picture as a Resource.
     */
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