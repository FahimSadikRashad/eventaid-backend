package com.example.eventlybackend.evently.repository;


import com.example.eventlybackend.evently.model.User;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {

//    @Query("select u from User u where u.name like :key")
    User findUserByName( String userName);

}
