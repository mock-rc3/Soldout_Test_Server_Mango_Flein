package com.example.demo.src.orderHistory;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHistoryProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderHistoryDao orderHistoryDao;
    private final JwtService jwtService;

    @Autowired
    public OrderHistoryProvider(OrderHistoryDao orderHistoryDao, JwtService jwtService) {
        this.orderHistoryDao = orderHistoryDao;
        this.jwtService = jwtService;

    }
}
