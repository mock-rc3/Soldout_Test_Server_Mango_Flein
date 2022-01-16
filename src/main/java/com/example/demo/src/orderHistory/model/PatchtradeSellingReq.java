package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PatchtradeSellingReq {
    private int order_id;
    private String address;
    private String bank;
    private int account;
    private String seller_name;
}
