package com.example.app.app.controller;

import com.example.app.app.dto.MessageDTO;
import com.example.app.app.dto.UserRequestDTO;
import com.example.app.app.dto.UserResponseDTO;
import com.example.app.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/createUser")

    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO savedUser = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);


    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<MessageDTO> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
       return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("User Deleted"));
    }

}
