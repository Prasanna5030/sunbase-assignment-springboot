package com.sunbase.javaassignment.auth;

import com.sunbase.javaassignment.entity.User;
import com.sunbase.javaassignment.repository.UserRepository;
import com.sunbase.javaassignment.securityconfig.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository userRepository;


    private final JwtService jwtService;


    private final AuthenticationManager authenticationManager;


    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()) {
            log.info("inside register method");
            var userMap = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .street(request.getStreet())
                    .city(request.getCity())
                    .state(request.getState())
                    .country(request.getCountry())
                    .phone(request.getPhone())
                    .role(request.getRole())
                    .build();

            var userReq = userRepository.save(userMap);

            String token = jwtService.generateToken(userReq);
            var authResponse = AuthenticationResponse.builder().token(token).build();
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request){

        log.info("inside authenticate method");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        var authResponse = AuthenticationResponse.builder().token(token).build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);


    }
}
