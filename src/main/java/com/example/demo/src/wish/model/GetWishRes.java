package com.example.demo.src.wish.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetWishRes {
    private String product_name;
    private String color;
    private String brand;
    private int price;
    private String size_name;
}
