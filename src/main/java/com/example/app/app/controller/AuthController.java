package com.example.app.app.controller;

import com.example.app.app.dto.AuthRequest;
import com.example.app.app.dto.MessageDTO;
import com.example.app.app.security.JWTAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTAuth jwtAuth;
@PostMapping("/login")
    public ResponseEntity<MessageDTO> login(@RequestBody AuthRequest authRequest){

    try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        String token = jwtAuth.generateToken(authRequest.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO(token));




    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageDTO("Invalid username/password"));
    }


}
}
