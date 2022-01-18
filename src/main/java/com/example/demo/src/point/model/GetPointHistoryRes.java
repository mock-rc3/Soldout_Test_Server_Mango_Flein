package com.example.demo.src.point.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
public class GetPointHistoryRes {
    private int point_id;
    private int point;
    private String type;
    private int status;
    private Timestamp create_at;
}
