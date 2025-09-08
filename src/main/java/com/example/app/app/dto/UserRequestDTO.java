package com.example.app.app.dto;


import com.example.app.app.utils.UserCatagory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    private String userName;
    private String password;
    private UserCatagory userCatagory;
}
