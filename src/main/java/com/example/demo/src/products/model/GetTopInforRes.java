package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTopInforRes {
    private String brand_image;
    private String product_name;
    private String product_name_eng;
    private int max_count;
    private String diff_count;
    private String percent;
}
