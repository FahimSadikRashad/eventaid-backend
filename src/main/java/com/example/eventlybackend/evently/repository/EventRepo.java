package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event,Integer> {
}
