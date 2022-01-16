package com.example.demo.src.orderHistory;

import com.example.demo.config.BaseException;
import com.example.demo.src.orderHistory.model.GetDealNumberRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

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
    public List<GetDealNumberRes> getDealNumber(int userId) throws BaseException {
        try{
            List<GetDealNumberRes> getDealNumberRes = orderHistoryDao.getDealNumber(userId);
            return getDealNumberRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}


