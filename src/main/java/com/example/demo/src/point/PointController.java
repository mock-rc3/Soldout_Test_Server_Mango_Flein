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
     *  [GET] /points
     *  @return BaseResponse<List<>>
     */
    @ResponseBody
    @GetMapping("/history")
    public BaseResponse<List<GetPointHistoryRes>> getPontHistory(){
        try {
            int user_id = jwtService.getUserId();
            List<GetPointHistoryRes> getPointHistoryRes = pointProvider.getPontHistory(user_id);
            return new BaseResponse<>(getPointHistoryRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     *  타입별 포인트 내역 조회 API
     *  [GET] /points/:type
     *  @return BaseResponse<List<>>
     */
    @ResponseBody
    @GetMapping("/history/{type}")
    public BaseResponse<List<GetPointHistoryRes>> getPontHistoryByType(@PathVariable("type") String type){
        try {
            int user_id = jwtService.getUserId();
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
     *  포인트 내역 조회 API
     *  [GET] /points
     *  @return BaseResponse<>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetPointRes> getPontHistoryByType(){
        try {
            int user_id = jwtService.getUserId();
            GetPointRes getPointHistoryRes = pointProvider.getPoint(user_id);
            return new BaseResponse<>(getPointHistoryRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
