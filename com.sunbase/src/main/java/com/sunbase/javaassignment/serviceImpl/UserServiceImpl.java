package com.sunbase.javaassignment.serviceImpl;

import com.sunbase.javaassignment.entity.Role;
import com.sunbase.javaassignment.entity.User;
import com.sunbase.javaassignment.repository.UserRepository;
import com.sunbase.javaassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> checkToken() {
        return new ResponseEntity<>("true", HttpStatus.OK);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User updateUser(Map<String, String> requestMap) {
        Optional<User> user = userRepository.findById(Integer.valueOf(requestMap.get("id")));

        if(user.isPresent()) {
            User user1 = user.get();
            user1.setFirstName(requestMap.get("firstName"));
            user1.setLastName(requestMap.get("lastName"));
            user1.setStreet(requestMap.get("street"));
            user1.setCity(requestMap.get("city"));
            user1.setCountry(requestMap.get("country"));
            user1.setPhone(requestMap.get("phone"));
            user1.setRole(Role.valueOf(requestMap.get("role")));
          return  userRepository.save(user1);
        }
        else{
            return null;
        }
    }

    @Override
    public String delete(Map<String, String> requestMap) {
      Optional<User> user=  userRepository.findById(Integer.valueOf(requestMap.get("id")));
      if(user.isPresent()) {
          userRepository.delete(user.get());
          return "user deleted";
      }
      else {
          return "user not found";
      }

    }

}
