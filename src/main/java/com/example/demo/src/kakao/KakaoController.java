package com.example.demo.src.kakao;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.kakao.model.PostTokenRes;
import com.example.demo.src.kakao.model.GetUserInfoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/kakao")
public class KakaoController {
    @Autowired
    private KakaoService kakaoService;

    /**
     * 카카오 로그인 ACCESS TOKEN 받기 API
     * [POST] /kakao/token?code
     * @param code 인가 코드
     * @return BaseResponse<GetTokenRes>
     */
    @PostMapping("/token")
    public BaseResponse<PostTokenRes> token(@RequestParam(value = "code", required = false) String code) throws Exception{
        PostTokenRes postTokenRes = kakaoService.getAccessToken(code);
        return new BaseResponse<>(postTokenRes);
    }

    /**
     * 카카오 로그인 유저 정보 조회 API
     * [GET] /kakao/info
     * @param code 인가 코드
     * @return BaseResponse<GetUserInfoRes>
     * @throws Exception
     */
    @GetMapping("/info")
    public BaseResponse<GetUserInfoRes> info(@RequestParam(value = "code", required = false) String code) throws Exception{
        PostTokenRes postTokenRes = kakaoService.getAccessToken(code);
        GetUserInfoRes getUserInfoRes = kakaoService.getUserInfo(postTokenRes);
        return new BaseResponse<>(getUserInfoRes);
    }
}
