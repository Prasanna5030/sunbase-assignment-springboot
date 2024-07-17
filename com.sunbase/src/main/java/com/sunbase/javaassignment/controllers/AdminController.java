package com.sunbase.javaassignment.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/home/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    private String hello(){
        return "secured end point :: admin - get ";
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyAuthority('admin:create')")
    private String hi(){
        return "secured end point :: admin - post ";
    }
}
