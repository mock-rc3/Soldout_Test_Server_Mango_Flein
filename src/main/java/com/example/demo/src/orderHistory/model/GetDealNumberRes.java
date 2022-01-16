package com.example.demo.src.orderHistory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDealNumberRes {
    private int buy_bidding;
    private int buy_end;
    private int sell_bidding;
    private int sell_end;
}

