package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetTradePriceRes {
    private int order_id;
    private String size_name;
    private Timestamp complete_time;
    private int time_diff;
    private int total_price;
}
