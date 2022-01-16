package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderHistory {
    private int order_id;
    private int user_id;
    private int product_id;
    private int size_id;
    private int trader_id;
    private String type;
    private String hope_price;
    private String total_price;
    private String order_state;
    private String point;
}
