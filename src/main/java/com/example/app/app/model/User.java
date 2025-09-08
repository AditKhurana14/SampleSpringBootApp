package com.example.app.app.model;

import com.example.app.app.utils.UserCatagory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long userId;

    @Column(name = "User_Name")
   private String userName;
    @Column(name = "Password")
   private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "User_Type" , nullable = false)
    private UserCatagory userCatagory;

}
