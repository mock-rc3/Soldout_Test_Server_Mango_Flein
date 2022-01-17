package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDealDetailRes {
    private int order_id;
    private int product_id;
    private String size_name;
    private String url;
    private String brand_image;
    private String product_name;
    private int hope_price;
}
