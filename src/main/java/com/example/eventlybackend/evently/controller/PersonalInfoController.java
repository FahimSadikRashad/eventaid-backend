package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.payloads.PersonalInfoDto;

import com.example.eventlybackend.evently.services.PersonalInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**

 The {@code PersonalInfoController} class is a REST controller that handles

 requests related to personal information of users.
 */
@RestController
@RequestMapping("/api/personalInfo")
@CrossOrigin("http://localhost:3000")
public class PersonalInfoController {
    @Autowired
    private PersonalInfoService personalInfoService;

    /**

     Updates the personal information of a user.
     @param personalInfoDto The updated personal information of the user.
     @param uid The ID of the user.
     @return A ResponseEntity containing the updated PersonalInfoDto.
     */
    @PostMapping("/{userId}")
    public ResponseEntity<PersonalInfoDto> updateUser( @RequestBody PersonalInfoDto personalInfoDto, @PathVariable("userId") Integer uid) {
        System.out.println(personalInfoDto.getLastname());
        PersonalInfoDto updatedPersonalInfo = this.personalInfoService.updateUserProfile(personalInfoDto, uid);
        return ResponseEntity.ok(updatedPersonalInfo);
    }

    /**

     Retrieves the personal information of a single user.
     @param userId The ID of the user.
     @return A ResponseEntity containing the PersonalInfoDto of the user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<PersonalInfoDto> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.personalInfoService.getUserProfile(userId));
    }
}
