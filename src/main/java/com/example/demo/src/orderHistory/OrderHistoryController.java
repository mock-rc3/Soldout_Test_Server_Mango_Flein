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

    @ResponseBody
    @PatchMapping("/{orderId}/delete")
    public BaseResponse<String> modifyOrder(@PathVariable("orderId") int orderId) {

        try {

            PatchDeleteOrderReq patchDeleteOrderReq = new PatchDeleteOrderReq(orderId);
            orderHistoryService.modifyOrder(patchDeleteOrderReq);
            String result = "삭제 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/{userId}/{types}/deal")
    public BaseResponse<List<GetDealDetailRes>> getDealDetail(@PathVariable("userId") int userId,@PathVariable("types") String types) {
        try{
            List<GetDealDetailRes> getDealDetailRes = orderHistoryProvider.getDealDetail(userId,types);
            return new BaseResponse<>(getDealDetailRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/{userId}/{types}/complete")
    public BaseResponse<List<GetDealDetailRes>> getDealComplete(@PathVariable("userId") int userId,@PathVariable("types") String types) {
        try{
            List<GetDealDetailRes> getDealDetailRes = orderHistoryProvider.getDealComplete(userId,types);
            return new BaseResponse<>(getDealDetailRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 타입별 입찰 주문 조회 API
     * [GET] /orderhistory/:productId/:type
     * 사이즈별 입찰 주문 조회 API
     * [GET] /orderhistory/:productId/:type?size=
     *
     * @return BaseResponse<List < GetOrderRes>>
     */
    @ResponseBody
    @GetMapping("/{productId}/{type}")
    public BaseResponse<List<GetOrderRes>> getOrderByType(@PathVariable("productId") int product_id, @PathVariable("type") String type, @RequestParam(required = false) Integer size_id) {
        try {
            int user_id = jwtService.getUserId();
            if (size_id == null) {
                List<GetOrderRes> getOrderRes = orderHistoryProvider.getOrderByType(product_id, user_id, type);
                return new BaseResponse<>(getOrderRes);
            }
            List<GetOrderRes> getOrderRes = orderHistoryProvider.getOrderBySize(product_id, user_id, type, size_id);
            return new BaseResponse<>(getOrderRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즉시 구매가/판매가 조회 API
     * [GET] /orderhistory/price/:productId/:type
     *
     * @return BaseResponse<List < GetOrderPriceRes>>
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
                return new BaseResponse<>(DATABASE_ERROR); //잘못된 타입 validation
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 사이즈별 즉시 구매가/판매가 조회 API
     * [GET] /orderhistory/price/:type/:productId/:sizeId
     *
     * @return BaseResponse<List < GetOrderPriceRes>>
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
                return new BaseResponse<>(DATABASE_ERROR);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PatchMapping("/trade/buy")
    //@Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> tradeBuy( @RequestBody  PatchtradeBuyReq patchtradeBuyReq) {
        try {
            int trader_id = jwtService.getUserId();
            orderHistoryService.tradeBuy(trader_id, patchtradeBuyReq);
            String result = "성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PatchMapping("/trade/selling")
    //@Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> tradeSelling( @RequestBody PatchtradeSellingReq patchtradeSellingReq) {
        try {
            int trader_id = jwtService.getUserId();
            orderHistoryService.tradeSelling(trader_id,patchtradeSellingReq);
            String result = "성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

