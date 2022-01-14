package com.example.demo.src.magazine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMagazineDetailRes {
    private String type;
    private String title;
    private String contents_url;
}
