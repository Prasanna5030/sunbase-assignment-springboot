package com.sunbase.javaassignment.repository;

import com.sunbase.javaassignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u where u.email=:email")
    Optional<User> findByEmail(@RequestParam("email") String email);
}
