package com.example.eventlybackend.evently.repository;

import com.example.eventlybackend.evently.model.FoodorService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodorServiceRepo extends JpaRepository<FoodorService,Integer> {
}
