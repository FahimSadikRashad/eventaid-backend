package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.PersonalInfo;
import com.example.eventlybackend.evently.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepo extends JpaRepository<PersonalInfo,Integer> {
    PersonalInfo findByUser(User user);
}
