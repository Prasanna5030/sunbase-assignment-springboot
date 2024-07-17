package com.sunbase.javaassignment.controllers;

import com.sunbase.javaassignment.entity.User;
import com.sunbase.javaassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/home/user")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('user:read')")
    private String hello(){
        return "secured end point :: user - get ";
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyAuthority('user:create')")
    private String hi(){
        return "secured end point :: user - post ";
    }


    @GetMapping("/allusers")
    @PreAuthorize("hasAnyAuthority('user:read','admin:read', 'user:create', 'admin:create')")
    public List<User> getAllUsers(){
       return userService.getAllUsers();

    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('user:read','admin:read', 'user:create', 'admin:create')")
    public ResponseEntity<User> updateUser(@RequestParam Map<String, String> requestMap){

       return new ResponseEntity<>( userService.updateUser(requestMap), HttpStatus.OK);

    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('user:read','admin:read', 'user:create', 'admin:create')")
    public ResponseEntity<String> deleteUser(@RequestParam Map<String, String> requestMap){
        return new ResponseEntity<>(userService.delete(requestMap), HttpStatus.OK);

    }
}
