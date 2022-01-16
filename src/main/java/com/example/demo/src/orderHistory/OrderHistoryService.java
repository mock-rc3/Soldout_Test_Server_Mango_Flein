package com.example.demo.src.orderHistory;

import com.example.demo.config.BaseException;
import com.example.demo.src.orderHistory.model.PatchtradeBuyReq;
import com.example.demo.src.orderHistory.model.PatchtradeSellingReq;
import com.example.demo.src.user.model.PatchDeleteUserReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.FAIL_DELETE_USER;

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
    public void tradeSelling(int trader_id, PatchtradeSellingReq patchtradeSellingReq) throws BaseException {
        try{
            int result = orderHistoryDao.tradeSelling(trader_id,patchtradeSellingReq);
            if(result == 0){
                throw new BaseException(DATABASE_ERROR);
            }

            int user_id = orderHistoryDao.getUserId(patchtradeSellingReq.getOrder_id());
            int plusPoint = orderHistoryDao.getPlusPoint(patchtradeSellingReq.getOrder_id());
            orderHistoryDao.plusPoint(user_id, plusPoint);
            orderHistoryDao.createPointHistory(user_id,patchtradeSellingReq.getOrder_id(),plusPoint,"적립");
            int minusPoint = orderHistoryDao.getMinusPoint(patchtradeSellingReq.getOrder_id());
            orderHistoryDao.minusPoint(user_id, minusPoint);
            orderHistoryDao.createPointHistory(user_id,patchtradeSellingReq.getOrder_id(),minusPoint,"사용");
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void tradeBuy(int trader_id, PatchtradeBuyReq patchtradeBuyReq) throws BaseException {
        try{
            int result = orderHistoryDao.tradeBuy(trader_id, patchtradeBuyReq);
            if(result == 0){
                throw new BaseException(DATABASE_ERROR);
            }
            int plusPoint = orderHistoryDao.getPlusPoint(patchtradeBuyReq.getOrder_id());
            orderHistoryDao.plusPoint(trader_id, plusPoint);
            orderHistoryDao.createPointHistory(trader_id,patchtradeBuyReq.getOrder_id(),plusPoint,"적립");
            orderHistoryDao.minusPoint(trader_id, patchtradeBuyReq.getPoint());
            orderHistoryDao.createPointHistory(trader_id,patchtradeBuyReq.getOrder_id(),patchtradeBuyReq.getPoint(),"사용");

       } catch(Exception exception){
           throw new BaseException(DATABASE_ERROR);
       }
    }
}
