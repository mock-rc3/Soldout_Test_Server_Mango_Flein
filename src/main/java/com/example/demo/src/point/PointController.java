package com.example.demo.src.point;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.point.model.GetPointHistoryRes;
import com.example.demo.src.point.model.GetPointRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/points")
public class PointController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PointProvider pointProvider;
    @Autowired
    private final JwtService jwtService;

    public PointController(PointProvider pointProvider, JwtService jwtService) {
        this.pointProvider = pointProvider;
        this.jwtService = jwtService;
    }

    /**
     *  포인트 내역 조회 API
     *  [GET] /points/:userId
     *  @param userId
     *  @return BaseResponse<List<GetPointHistoryRes>>
     */
    @ResponseBody
    @GetMapping("/history/{userId}")
    public BaseResponse<List<GetPointHistoryRes>> getPontHistory(@PathVariable("userId") int userId){
        try {
            int user_id = jwtService.getUserId();
            if (userId != user_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetPointHistoryRes> getPointHistoryRes = pointProvider.getPontHistory(user_id);
            return new BaseResponse<>(getPointHistoryRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  타입별 포인트 내역 조회 API
     *  [GET] /points/:userId/:type
     *  @param userId
     *  @param type 포인트 타입 - 사용/적립/소멸/취소
     *  @return BaseResponse<List<GetPointHistoryRes>>
     */
    @ResponseBody
    @GetMapping("/history/{userId}/{type}")
    public BaseResponse<List<GetPointHistoryRes>> getPontHistoryByType(@PathVariable("userId") int userId, @PathVariable("type") String type){
        try {
            int user_id = jwtService.getUserId();
            if (userId != user_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(type.equals("취소")){
                List<GetPointHistoryRes> getPointHistoryRes = pointProvider.getCancelPontHistory(user_id);
                return new BaseResponse<>(getPointHistoryRes);
            }
            List<GetPointHistoryRes> getPointHistoryRes = pointProvider.getPontHistoryByType(user_id,type);
            return new BaseResponse<>(getPointHistoryRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  포인트 조회 API
     *  [GET] /points/:userId
     *  @param userId
     *  @return BaseResponse<GetPointRes>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<GetPointRes> getPoint(@PathVariable("userId") int userId){
        try {
            int user_id = jwtService.getUserId();
            if (userId != user_id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetPointRes getPointHistoryRes = pointProvider.getPoint(user_id);
            return new BaseResponse<>(getPointHistoryRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
