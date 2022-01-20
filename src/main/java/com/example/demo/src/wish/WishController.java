package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.wish.model.GetSizeRes;
import com.example.demo.src.wish.model.GetWishRes;
import com.example.demo.src.wish.model.PostWishReq;
import com.example.demo.src.wish.model.PostWishRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/wishes")
public class WishController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WishProvider wishProvider;
    @Autowired
    private final WishService wishService;
    @Autowired
    private final JwtService jwtService;;


    public WishController(WishProvider wishProvider, WishService wishService, JwtService jwtService) {
        this.wishProvider = wishProvider;
        this.wishService = wishService;
        this.jwtService = jwtService;
    }

    /**
     *  찜 목록 조회 API
     *  [GET] /wishes/:userId
     *  @param userId 유저 아이디
     *  @return BaseResponse<List<GetWishRes>>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetWishRes>> getWish(@PathVariable("userId") int userId){
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetWishRes> getWishRes = wishProvider.getWish(userIdByJwt);
            return new BaseResponse<>(getWishRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  사이즈 목록 조회 API
     *  [GET] /wishes/size/:productId
     *  @param product_id 상품 아이디
     *  @return BaseResponse<List<GetSizeRes>>
     */
    @ResponseBody
    @GetMapping("/size/{productId}")
    public BaseResponse<List<GetSizeRes>> getSize(@PathVariable ("productId") int product_id){
        try {
            int userIdByJwt = jwtService.getUserId();
            List<GetSizeRes> getSizeRes = wishProvider.getSize(userIdByJwt,product_id);
            return new BaseResponse<>(getSizeRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜 추가 API
     * [POST] /wishes/:userId
     * @param userId
     * @param postWishReq
     * @return BaseResponse<PostWishRes>
     */
    @ResponseBody
    @PostMapping("/{userId}")
    public BaseResponse<PostWishRes> createWish(@PathVariable("userId") int userId, @RequestBody PostWishReq postWishReq) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostWishRes postWishRes = wishService.createWish(userIdByJwt,postWishReq);
            return new BaseResponse<>(postWishRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 찜 삭제 API
     * [PATCH] /wishes/:wishId/:userId/delete
     * @param favoriteId 찜 아이디
     * @param userId 유저 아이디
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{wishId}/{userId}/delete")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> deleteWish(@PathVariable("wishId") int favoriteId, @PathVariable("userId") int userId) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            wishService.deleteWish(favoriteId);
            String result = "찜 삭제 성공";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
