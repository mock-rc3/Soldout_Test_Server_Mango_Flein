package com.example.demo.src.orderHistory;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.orderHistory.model.GetDealNumberRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ResponseBody
    @GetMapping("/{userId}/dealnumber")
    public BaseResponse<List<GetDealNumberRes>> getDealNumber(@PathVariable("userId") int userId) {
        try{

            List<GetDealNumberRes> getDealNumberRes = orderHistoryProvider.getDealNumber(userId);
            return new BaseResponse<>(getDealNumberRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
