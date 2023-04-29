package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.User;
import com.example.eventlybackend.evently.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepo extends JpaRepository<UserDetails,Integer> {
    List<UserDetails> findByUser(User user);
}
