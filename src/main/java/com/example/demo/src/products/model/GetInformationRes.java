package com.example.demo.src.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetInformationRes {
    private String brand;
    private String model_num;
    private String release_day;
    private String color;
    private int price;
    private String purchase_name;
    private String image_url;
}
