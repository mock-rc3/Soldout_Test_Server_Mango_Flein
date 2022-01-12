package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int user_id;
    private String id;
    private String password;
    private String phone_num;
    private String email;
    private String name;
    private String nickname;
    private int point;
}
