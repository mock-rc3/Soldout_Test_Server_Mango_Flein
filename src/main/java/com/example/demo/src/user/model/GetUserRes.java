package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int user_id;
    private String name;
    private String id;
    private String email;
    private String nickname;
    private String password;
    private String phone_num;
}

