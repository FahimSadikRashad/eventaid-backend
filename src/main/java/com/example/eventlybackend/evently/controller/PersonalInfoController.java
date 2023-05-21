package com.example.eventlybackend.evently.controller;

import com.example.eventlybackend.evently.payloads.PersonalInfoDto;

import com.example.eventlybackend.evently.services.PersonalInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personalInfo")
@CrossOrigin("http://localhost:3000")
public class PersonalInfoController {
    @Autowired
    private PersonalInfoService personalInfoService;

    @PostMapping("/{userId}")
    public ResponseEntity<PersonalInfoDto> updateUser( @RequestBody PersonalInfoDto personalInfoDto, @PathVariable("userId") Integer uid) {
        System.out.println(personalInfoDto.getLastname());
        PersonalInfoDto updatedPersonalInfo = this.personalInfoService.updateUserProfile(personalInfoDto, uid);
        return ResponseEntity.ok(updatedPersonalInfo);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<PersonalInfoDto> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.personalInfoService.getUserProfile(userId));
    }
}
