package com.sunbase.javaassignment.service;

import com.sunbase.javaassignment.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> checkToken();

    List<User> getAllUsers();

    User updateUser(Map<String, String> requestMap);

    String delete(Map<String, String> requestMap);
}
