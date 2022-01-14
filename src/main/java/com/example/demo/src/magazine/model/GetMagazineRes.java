package com.example.demo.src.magazine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetMagazineRes {
    private int magazine_id;
    private String type;
    private String title;
    private String image_url;
    private Timestamp created_at;
}
