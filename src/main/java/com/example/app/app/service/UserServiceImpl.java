package com.example.app.app.service;

import com.example.app.app.dto.UserRequestDTO;
import com.example.app.app.dto.UserResponseDTO;
import com.example.app.app.exception.UserNotFoundException;
import com.example.app.app.model.User;
import com.example.app.app.repository.UserRepository;
import com.example.app.app.utils.BeanMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BeanMapperUtils beanMapperUtils;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = mapper.map(userRequestDTO, User.class);
        User savedUserEntity = userRepository.save(user);

        return mapper.map(savedUserEntity,UserResponseDTO.class);



    }

    @Override
    public void deleteUser(Long id) {
userRepository.findById(id).orElseThrow(()->new UserNotFoundException("No User with Given id"));
userRepository.deleteById(id);
    }
}
