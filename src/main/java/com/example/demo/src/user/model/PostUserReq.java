package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String id;
    private String password;
    private String phone_num;
    private String email;
    private String name;
    private String nickname;
    private int point;
}
