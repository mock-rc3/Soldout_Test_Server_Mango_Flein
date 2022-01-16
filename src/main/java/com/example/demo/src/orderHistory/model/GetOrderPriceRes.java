package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderPriceRes {
    private int order_id;
    private int user_id;
    private int imt_price;
    private String size_name;
}
