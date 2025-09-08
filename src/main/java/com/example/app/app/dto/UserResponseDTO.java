package com.example.app.app.dto;

import com.example.app.app.utils.UserCatagory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String password;
    private UserCatagory userCatagory;

}
