package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDayAlarmRes {
    private String release_day;
    private int product_id;
    private String product_name;
    private String url;
    private String brand_image;
    private String alarm_check;
}