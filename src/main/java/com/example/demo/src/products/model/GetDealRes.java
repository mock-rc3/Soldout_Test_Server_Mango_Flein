package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDealRes {
    private int amount;
    private String size_name;
    private int hope_price;
}
