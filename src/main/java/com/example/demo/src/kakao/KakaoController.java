package com.example.demo.src.kakao;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.kakao.model.KakaoRes;
import com.example.demo.src.kakao.model.PostTokenRes;
import com.example.demo.src.kakao.model.GetUserInfoRes;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@RestController
@RequestMapping("/kakao")
public class KakaoController {

    KakaoRes kakaoRes = new KakaoRes();

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

    /**
     * 카카오페이 API
     * [GET] /kakaos/kakaopay
     *
     * @return String
     */
    @ResponseBody
    @GetMapping("/kakaopay")
    public String getKakaopay() {

        try {
            // 보내는 부분

            URL address = new URL("https://kapi.kakao.com/v1/payment/ready");
            HttpURLConnection connection = (HttpURLConnection) address.openConnection(); // 서버연결
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "KakaoAK e660e05128ea942478e813aab4073c28"); // 어드민 키
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true); // 서버한테 전달할게 있는지 없는지
            String parameter = "cid=TC0ONETIME" // 가맹점 코드
                    + "&partner_order_id=partner_order_id" // 가맹점 주문번호
                    + "&partner_user_id=partner_user_id" // 가맹점 회원 id
                    + "&item_name=초코파이" // 상품명
                    + "&quantity=1" // 상품 수량
                    + "&total_amount=3000" // 총 금액
                    + "&vat_amount=200" // 부가세
                    + "&tax_free_amount=0" // 상품 비과세 금액
                    + "&approval_url=http://3.37.226.93:9000/kakao/kakaopay/sucess" // 결제 성공 시
                    + "&fail_url=http://3.37.226.93:9000/kakao/kakaopay/fail" // 결제 실패 시
                    + "&cancel_url=http://3.37.226.93:9000/kakao/kakaopay/cancel"; // 결제 취소 시
            OutputStream send = connection.getOutputStream();
            DataOutputStream dataSend = new DataOutputStream(send);
            dataSend.writeBytes(parameter);
            dataSend.close();

            int result = connection.getResponseCode();
            InputStream receive;


            if(result == 200) {
                receive = connection.getInputStream();
            }else {
                receive = connection.getErrorStream();
            }



            InputStreamReader read = new InputStreamReader(receive);
            BufferedReader change = new BufferedReader(read);
            String line = "";
            String result2 = "";

            while ((line = change.readLine()) != null) {
                result2 += line;
            }
            JSONParser parser = new JSONParser();

            String tid = "";
            try{
                //books의 배열을 추출
                Object obj = parser.parse(result2);
                JSONObject jsonObj = (JSONObject) obj;
                tid = (String) jsonObj.get("tid");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            kakaoRes.setTid(tid);
            return result2;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 카카오페이 결제취소 API
     * [GET] /kakao/kakaopay/fail
     *
     * @return String
     */
    @ResponseBody
    @GetMapping("/kakaopay/fail")
    public String getKakaopayFail(@RequestParam("pg_token") String pg_token) {
        return  "결제취소";
    }

    /**
     * 카카오페이 결제실패 API
     * [GET] /kakao/kakaopay/fail
     *
     * @return String
     */
    @ResponseBody
    @GetMapping("/kakaopay/cancel")
    public String getKakaopayCancel(@RequestParam("pg_token") String pg_token) {
        return  "결제실패 ";
    }

    /**
     * 카카오페이 결제승인 API
     * [GET] /kakao/kakaopay/sucess
     *
     * @return String
     */
    @ResponseBody
    @GetMapping("/kakaopay/sucess")
    public String getKakaopaySucess(@RequestParam("pg_token") String pg_token) {
        String tidvalue =  kakaoRes.getTid();
        try {
            // 보내는 부분
            URL address = new URL("https://kapi.kakao.com/v1/payment/approve");
            HttpURLConnection connection = (HttpURLConnection) address.openConnection(); // 서버연결
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "KakaoAK e660e05128ea942478e813aab4073c28"); // 어드민 키
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true); // 서버한테 전달할게 있는지 없는지
            String parameter = "cid=TC0ONETIME" // 가맹점 코드
                    + "&tid="+ tidvalue//
                    + "&partner_order_id=partner_order_id" // 가맹점 주문번호
                    + "&partner_user_id=partner_user_id" // 가맹점 회원 id
                    + "&pg_token="+ pg_token;// 토큰
            // 결제 취소 시
            OutputStream send = connection.getOutputStream();
            DataOutputStream dataSend = new DataOutputStream(send);
            dataSend.writeBytes(parameter);
            dataSend.close();

            int result = connection.getResponseCode();
            InputStream receive;

            if(result == 200) {
                receive = connection.getInputStream();
            }else {
                receive = connection.getErrorStream();
            }
            // 읽는 부분
            InputStreamReader read = new InputStreamReader(receive);
            BufferedReader change = new BufferedReader(read);
            // 받는 부분
            return change.readLine();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
