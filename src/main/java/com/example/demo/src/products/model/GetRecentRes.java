package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRecentRes {
    private String times;
    private String complete_time;
    private String size_name;
    private int total_price;
}
