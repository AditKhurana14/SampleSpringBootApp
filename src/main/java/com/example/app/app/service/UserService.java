package com.example.app.app.service;

import com.example.app.app.dto.UserRequestDTO;
import com.example.app.app.dto.UserResponseDTO;

public interface UserService {
UserResponseDTO createUser(UserRequestDTO userRequestDTO);
}
