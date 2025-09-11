package com.example.app.app.service;

import com.example.app.app.dto.UserRequestDTO;
import com.example.app.app.dto.UserResponseDTO;
import com.example.app.app.exception.UserNotFoundException;
import com.example.app.app.model.User;
import com.example.app.app.repository.UserRepository;
import com.example.app.app.utils.BeanMapperUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BeanMapperUtils beanMapperUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = mapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUserEntity = userRepository.save(user);

        return mapper.map(savedUserEntity,UserResponseDTO.class);



    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
userRepository.findById(id).orElseThrow(()->new UserNotFoundException("No User with Given id"));
userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User fetchedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No User with Given id"));
        beanMapperUtils.copyNonNullProperties(userRequestDTO,fetchedUser);
        userRepository.save(fetchedUser);
        return mapper.map(fetchedUser,UserResponseDTO.class);



    }

    @Override
    public List<UserResponseDTO> getUsers() {
        List<User> allUsers = userRepository.findAll();
        return  allUsers.stream().map(user->mapper.map(user,UserResponseDTO.class))
                .collect(Collectors.toList());
    }
}
