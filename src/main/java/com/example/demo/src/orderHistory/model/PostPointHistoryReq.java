package com.example.demo.src.orderHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPointHistoryReq {
    private int user_id;
    private int order_id;
    private int point;
    private String type;
}
