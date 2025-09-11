package com.example.app.app.security;

import com.example.app.app.model.User;
import com.example.app.app.repository.UserRepository;
import com.example.app.app.utils.UserCatagory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataLoader {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void intit(){
        if(userRepository.count()==0){
            User user = User.builder()
                    .userName("adit")
                    .password(passwordEncoder.encode("123"))
                    .userCatagory(UserCatagory.valueOf("ADMIN"))

                    .build();

            userRepository.save(user);

        }
    }

}
