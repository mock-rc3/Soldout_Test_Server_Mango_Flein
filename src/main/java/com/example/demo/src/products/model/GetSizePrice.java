package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSizePrice {
    private int product_id;
    private int order_id;
    private String size_name;
    private int hope_price;
}
