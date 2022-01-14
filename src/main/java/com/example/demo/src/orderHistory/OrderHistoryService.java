package com.example.demo.src.orderHistory;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHistoryService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderHistoryDao orderHistoryDao;
    private final OrderHistoryProvider orderHistoryProvider;
    private final JwtService jwtService;


    @Autowired
    public OrderHistoryService(OrderHistoryDao orderHistoryDao, OrderHistoryProvider orderHistoryProvider, JwtService jwtService) {
        this.orderHistoryDao = orderHistoryDao;
        this.orderHistoryProvider = orderHistoryProvider;
        this.jwtService = jwtService;

    }
}
