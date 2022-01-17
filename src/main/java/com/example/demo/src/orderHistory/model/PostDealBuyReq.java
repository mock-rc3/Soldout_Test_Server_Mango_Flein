package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDealBuyReq {
    private int user_id;
    private int product_id;
    private int size_id;
    private String type;
    private int point;
    private int hope_price;
    private int term;
    private String address;
}
