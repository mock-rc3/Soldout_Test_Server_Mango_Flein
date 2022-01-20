package com.example.demo.src.orderHistory;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.orderHistory.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


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
    /**
     * 구매, 판매 내역 갯수 조회 API
     * [GET] /orderhistory/:userId/dealnumber
     *
     * @return BaseResponse<List < GetDealNumberRes>>
     */

    @ResponseBody
    @GetMapping("/{userId}/dealnumber")
    public BaseResponse<List<GetDealNumberRes>> getDealNumber(@PathVariable("userId") int userId) {
        try{
            int trader_id = jwtService.getUserId();
            if (userId != trader_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetDealNumberRes> getDealNumberRes = orderHistoryProvider.getDealNumber(userId);
            return new BaseResponse<>(getDealNumberRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  입찰 판매
     * [POST] /orderhistory/selling
     *
     * @return BaseResponse<List < PostDealRes>>
     */
    @ResponseBody
    @PostMapping("/selling")
    public BaseResponse<PostDealRes> createDealSell(@RequestBody PostDealSellReq postDealSellReq) {
        try{
            PostDealRes postDealRes = orderHistoryService.createDealSell(postDealSellReq);
            return new BaseResponse<>(postDealRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  입찰 구매
     * [POST] /orderhistory/buy
     *
     * @return BaseResponse<List < PostDealRes>>
     */
    @ResponseBody
    @PostMapping("/buy")
    public BaseResponse<PostDealRes> createDealBuy(@RequestBody PostDealBuyReq postDealBuyReq) {
        try{
            PostDealRes postDealRes = orderHistoryService.createDealBuy(postDealBuyReq);
            return new BaseResponse<>(postDealRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  입찰 내역 삭제
     * [PATCH] /orderhistory/trade/:orderId/delete
     *
     * @return BaseResponse<patchDeleteOrderReq>
     */
    @ResponseBody
    @PatchMapping("/trade/{orderId}/delete")
    public BaseResponse<String> modifyOrder(@PathVariable("orderId") int orderId) {
        try {
            if(orderHistoryProvider.checkOrderIdExist(orderId) == 0){
                return new BaseResponse<>(NOT_ORDERHISTORY_EXISTS_ORDERID);
            }
            PatchDeleteOrderReq patchDeleteOrderReq = new PatchDeleteOrderReq(orderId);
            orderHistoryService.modifyOrder(patchDeleteOrderReq);
            String result = "삭제 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *   구매,판매 입찰 내역 상세 조회
     * [GET] /orderhistory/:userId/:types/deal
     *
     * @return BaseResponse<List < GetDealDetailRes>>
     */
    @ResponseBody
    @GetMapping("/{userId}/{types}/deal")
    public BaseResponse<List<GetDealDetailRes>> getDealDetail(@PathVariable("userId") int userId,@PathVariable("types") String types) {
        try{
            int trader_id = jwtService.getUserId();
            if (userId != trader_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(orderHistoryProvider.checkTypesExist(types) == 0){
                return new BaseResponse<>(NOT_PRODUCTS_EXISTS_TYPES);
            }
            List<GetDealDetailRes> getDealDetailRes = orderHistoryProvider.getDealDetail(userId,types);
            return new BaseResponse<>(getDealDetailRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *   구매,판매 완료 내역 상세 조회
     * [GET] /orderhistory/:userId/:types/complete
     *
     * @return BaseResponse<List < GetDealDetailRes>>
     */
    @ResponseBody
    @GetMapping("/{userId}/{types}/complete")
    public BaseResponse<List<GetDealDetailRes>> getDealComplete(@PathVariable("userId") int userId,@PathVariable("types") String types) {
        try{
            int trader_id = jwtService.getUserId();
            if (userId != trader_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            if(orderHistoryProvider.checkTypesExist(types) == 0){
                return new BaseResponse<>(NOT_PRODUCTS_EXISTS_TYPES);
            }
            List<GetDealDetailRes> getDealDetailRes = orderHistoryProvider.getDealComplete(userId,types);
            return new BaseResponse<>(getDealDetailRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 타입별 입찰 주문 조회 API
     * [GET] /orderhistory/:productId/:type
     * @param product_id
     * @param type 입찰 타입 - 구매/판매
     * @return BaseResponse<List < GetOrderRes>>
     */
    @ResponseBody
    @GetMapping("/{productId}/{type}")
    public BaseResponse<List<GetOrderRes>> getOrderByType(@PathVariable("productId") int product_id, @PathVariable("type") String type) {
        try {
            int user_id = jwtService.getUserId();
                List<GetOrderRes> getOrderRes = orderHistoryProvider.getOrderByType(product_id, user_id, type);
                return new BaseResponse<>(getOrderRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 사이즈별 입찰 주문 조회 API
     * [GET] /orderhistory/:productId/:type/:size
     * @param product_id
     * @param type 입찰 타입 - 구매/판매
     * @param size_id
     * @return BaseResponse<List<GetOrderRes>>
     */
    @ResponseBody
    @GetMapping("/{productId}/{type}/{sizeId}")
    public BaseResponse<List<GetOrderRes>> getOrderBySize(@PathVariable("productId") int product_id, @PathVariable("type") String type, @PathVariable("sizeId") int size_id) {
        try {
            int user_id = jwtService.getUserId();
            List<GetOrderRes> getOrderRes = orderHistoryProvider.getOrderBySize(product_id, user_id, type, size_id);
            return new BaseResponse<>(getOrderRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즉시 구매가/판매가 조회 API
     * [GET] /orderhistory/price/:type/:productId
     * @param product_id
     * @param type 거래 타입 - 구매/판매
     * @return BaseResponse<List<GetOrderPriceRes>>
     */
    @ResponseBody
    @GetMapping("/price/{type}/{productId}")
    public BaseResponse<List<GetOrderPriceRes>> getPrice(@PathVariable("productId") int product_id, @PathVariable("type") String type) {
        try {
            int user_id = jwtService.getUserId();
            if (type.equals("buy")) { //즉시 구매가 조회
                List<GetOrderPriceRes> getOrderPriceRes = orderHistoryProvider.getMinPrice(product_id, user_id);
                return new BaseResponse<>(getOrderPriceRes);
            } else if (type.equals("sell")) {//즉시 판매가 조회
                List<GetOrderPriceRes> getOrderPriceRes = orderHistoryProvider.getMaxPrice(product_id, user_id);
                return new BaseResponse<>(getOrderPriceRes);
            } else {
                return new BaseResponse<>(ONLY_BUY_SELL); //잘못된 타입 validation
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사이즈별 즉시 구매가/판매가 조회 API
     * @param type
     * @param product_id
     * @param size_id
     * [GET] /orderhistory/price/:type/:productId/:sizeId
     * @return BaseResponse<List<GetOrderPriceRes>>
     */
    @ResponseBody
    @GetMapping("/price/{type}/{productId}/{sizeId}")
    public BaseResponse<GetOrderPriceRes> getPriceBySize(@PathVariable("type") String type, @PathVariable("productId") int product_id, @PathVariable("sizeId") int size_id) {
        try {
            int user_id = jwtService.getUserId();
            if (type.equals("buy")) {
                GetOrderPriceRes getOrderPriceRes = orderHistoryProvider.getMinPriceBySize(product_id, user_id, size_id);
                return new BaseResponse<>(getOrderPriceRes);
            } else if (type.equals("sell")) {
                GetOrderPriceRes getOrderPriceRes = orderHistoryProvider.getMaxPriceBySize(product_id, user_id, size_id);
                return new BaseResponse<>(getOrderPriceRes);
            } else {
                return new BaseResponse<>(ONLY_BUY_SELL);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즉시 구매 API
     * [PATCH] /orderhistory/trade/selling/:userId
     * @param userId
     * @param patchtradeBuyReq
     * @return
     */
    @ResponseBody
    @PatchMapping("/trade/buy/{userId}")
    public BaseResponse<String> tradeBuy(@PathVariable("userId") int userId, @RequestBody  PatchtradeBuyReq patchtradeBuyReq) {
        try {
            int trader_id = jwtService.getUserId();
            if (userId != trader_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(orderHistoryProvider.checkOrderIdExist(patchtradeBuyReq.getOrder_id()) == 0){
                return new BaseResponse<>(NOT_ORDERHISTORY_EXISTS_ORDERID);
            }
            orderHistoryService.tradeBuy(trader_id, patchtradeBuyReq);
            String result = "성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즉시 판매 API
     * [PATCH] /orderhistory/trade/selling/:userId
     * @param userId
     * @param patchtradeSellingReq
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/trade/selling/{userId}")
    public BaseResponse<String> tradeSelling(@PathVariable("userId") int userId, @RequestBody PatchtradeSellingReq patchtradeSellingReq) {
        try {
            int trader_id = jwtService.getUserId();
            if (userId != trader_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(orderHistoryProvider.checkOrderIdExist(patchtradeSellingReq.getOrder_id()) == 0){
                return new BaseResponse<>(NOT_ORDERHISTORY_EXISTS_ORDERID);
            }
            orderHistoryService.tradeSelling(trader_id,patchtradeSellingReq);
            String result = "성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 최근거래가 조회 API
     * [GET] /orderhistory/trade/:productId
     * @param product_id
     * @return BaseResponse<List<GetTradePriceRes>>
     */
    @ResponseBody
    @GetMapping("/trade/{productId}")
    public BaseResponse<List<GetTradePriceRes>> getTradePrice(@PathVariable("productId") int product_id) {
        try {
            int user_id = jwtService.getUserId();
            List<GetTradePriceRes> getTradePriceRes = orderHistoryProvider.getTradePrice(product_id);
            return new BaseResponse<>(getTradePriceRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 최근거래가 사이즈 별 조회 API
     * [GET] /orderhistory/trade/:productId/:sizeId
     * @param product_id
     * @param size_id
     * @return BaseResponse<List <GetTradePriceRes>>
     */
    @ResponseBody
    @GetMapping("/trade/{productId}/{sizeId}")
    public BaseResponse<List<GetTradePriceRes>> getTradePrice(@PathVariable("productId") int product_id, @PathVariable("sizeId") int size_id) {
        try {
            int user_id = jwtService.getUserId();
            List<GetTradePriceRes> getTradePriceRes = orderHistoryProvider.getTradePriceBySize(product_id, size_id);
            return new BaseResponse<>(getTradePriceRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

