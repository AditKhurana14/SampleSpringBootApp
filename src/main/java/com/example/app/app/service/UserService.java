package com.example.app.app.service;

import com.example.app.app.dto.UserRequestDTO;
import com.example.app.app.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
UserResponseDTO createUser(UserRequestDTO userRequestDTO);
void deleteUser(Long id);
UserResponseDTO updateUser(Long id,UserRequestDTO userRequestDTO);
List<UserResponseDTO> getUsers();
}
