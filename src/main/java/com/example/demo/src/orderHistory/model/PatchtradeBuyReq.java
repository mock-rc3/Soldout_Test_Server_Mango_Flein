package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchtradeBuyReq {
    private int order_id;
    private int point; //사용 포인트
    private String address;
}
