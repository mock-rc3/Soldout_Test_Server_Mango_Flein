package com.example.demo.src.notices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostNoticeReq {
    private int user_id;
    private int product_id;
}
