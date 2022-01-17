package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReleaseRes {
    private int product_id;
    private String product_name;
    private int price;
    private String url;
    private String release_day;
}
