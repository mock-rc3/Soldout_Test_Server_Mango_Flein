package com.example.demo.src.orderHistory;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderhistory")
public class OrderHistoryController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderHistoryProvider orderHistoryProvider;
    @Autowired
    private final OrderHistoryService orderHistoryService;
    @Autowired
    private final JwtService jwtService;

    public OrderHistoryController(OrderHistoryProvider orderHistoryProvider, OrderHistoryService orderHistoryService, JwtService jwtService) {
        this.orderHistoryProvider = orderHistoryProvider;
        this.orderHistoryService = orderHistoryService;
        this.jwtService = jwtService;
    }


}
