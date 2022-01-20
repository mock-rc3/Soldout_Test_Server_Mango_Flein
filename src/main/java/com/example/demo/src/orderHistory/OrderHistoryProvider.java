package com.example.demo.src.orderHistory;

import com.example.demo.config.BaseException;
import com.example.demo.src.magazine.model.GetMagazineRes;
import com.example.demo.src.orderHistory.model.*;
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
        try {
            List<GetDealNumberRes> getDealNumberRes = orderHistoryDao.getDealNumber(userId);
            return getDealNumberRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDealDetailRes> getDealDetail(int user_id, String types) throws BaseException {
        try {
            List<GetDealDetailRes> getDealDetailRes = orderHistoryDao.getDealDetail(user_id, types);
            return getDealDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDealDetailRes> getDealComplete(int user_id, String types) throws BaseException {
        try {
            List<GetDealDetailRes> getDealDetailRes = orderHistoryDao.getDealComplete(user_id, types);
            return getDealDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public int checkOrderIdExist(int orderId) throws BaseException {
        try{
            return orderHistoryDao.checkOrderIdExist(orderId);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkTypesExist(String types) throws BaseException {
        try{
            return orderHistoryDao.checkTypesExist(types);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 타입별 입찰 주문 조회
    public List<GetOrderRes> getOrderByType(int product_id, int user_id, String type) throws BaseException {
        try {
            List<GetOrderRes> getOrderRes = orderHistoryDao.getOrderByType(product_id, user_id, type);
            return getOrderRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 사이즈별 입찰 주문 조회
    public List<GetOrderRes> getOrderBySize(int product_id, int user_id, String type, int size_id) throws BaseException {
        try {
            List<GetOrderRes> getOrderRes = orderHistoryDao.getOrderBySize(product_id, user_id, type, size_id);
            return getOrderRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 즉시 판매가 조회
    public List<GetOrderPriceRes> getMaxPrice(int product_id, int user_id) throws BaseException {
        try {
            List<GetOrderPriceRes> getOrderPriceRes = orderHistoryDao.getMaxPrice(product_id, user_id);
            return getOrderPriceRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 즉시 구매가 조회
    public List<GetOrderPriceRes> getMinPrice(int product_id, int user_id) throws BaseException {
        try {
            List<GetOrderPriceRes> getOrderPriceRes = orderHistoryDao.getMinPrice(product_id, user_id);
            return getOrderPriceRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 사이즈별 즉시 판매가 조회
    public GetOrderPriceRes getMaxPriceBySize(int product_id, int user_id, int size_id) throws BaseException {
        try {
            GetOrderPriceRes getOrderPriceRes = orderHistoryDao.getMaxPriceBySize(product_id, user_id, size_id);
            return getOrderPriceRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 사이즈별 즉시 구매가 조회
    public GetOrderPriceRes getMinPriceBySize(int product_id, int user_id, int size_id) throws BaseException {
        try {
            GetOrderPriceRes getOrderPriceRes = orderHistoryDao.getMinPriceBySize(product_id, user_id, size_id);
            return getOrderPriceRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 최근 거래가 내역 조회
    public List<GetTradePriceRes> getTradePrice(int product_id) throws BaseException {
        try {
            List<GetTradePriceRes> getTradePriceRes = orderHistoryDao.getTradePrice(product_id);
            return getTradePriceRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // GET 최근거래가 사이즈 별 조회
    public List<GetTradePriceRes> getTradePriceBySize(int product_id, int size_id) throws BaseException {
        try {
            List<GetTradePriceRes> getTradePriceRes = orderHistoryDao.getTradePriceBySize(product_id, size_id);
            return getTradePriceRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
