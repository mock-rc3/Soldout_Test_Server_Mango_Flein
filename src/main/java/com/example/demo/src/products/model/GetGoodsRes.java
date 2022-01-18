package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetGoodsRes {
    private int product_id;
    private String url;
    private String product_name;
    private int max_count;
    private int percent;
}
