package com.example.demo.src.message.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMessageReq {
    private String phone_num;
    private String cer_num;
}
