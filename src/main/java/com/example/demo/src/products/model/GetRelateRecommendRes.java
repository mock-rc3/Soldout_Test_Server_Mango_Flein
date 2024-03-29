package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRelateRecommendRes {
    private int product_id;
    private String url;
    private String brand_image;
    private String product_name;
    private int total_price;
}
