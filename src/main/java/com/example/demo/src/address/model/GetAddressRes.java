package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class GetAddressRes {
        private String address_name;
        private String recipient;
        private String phone_num;
        private String address;
        private String detail;
        private int basic;
}
