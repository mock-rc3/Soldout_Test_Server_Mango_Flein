package com.example.demo.src.kakao;

import com.example.demo.config.BaseException;
import com.example.demo.src.kakao.model.PostTokenRes;
import com.example.demo.src.kakao.model.GetUserInfoRes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.demo.config.BaseResponseStatus.INVALID_CODE;

@Service
public class KakaoService {

    // POST ACCESS TOKEN 받기
    public PostTokenRes getAccessToken(String authorize_code) throws BaseException {
        String reqURL = "https://kauth.kakao.com/oauth/token";
        PostTokenRes postTokenRes;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=1b0e78e737a19ff1f244582aabb31d8f");
            sb.append("&redirect_uri=http://norangmango.shop:9000/kakao/login");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            postTokenRes = new PostTokenRes(
                    element.getAsJsonObject().get("access_token").getAsString(),
                    element.getAsJsonObject().get("refresh_token").getAsString());
            br.close();
            bw.close();
            return postTokenRes;
        } catch (Exception exception) {
            throw new BaseException(INVALID_CODE);
        }
    }

    // GET 유저 정보 조회
    public GetUserInfoRes getUserInfo (PostTokenRes postTokenRes) throws BaseException {
            String reqURL = "https://kapi.kakao.com/v2/user/me";
            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "Bearer " + postTokenRes.getAccess_Token());
                int responseCode = conn.getResponseCode();
                System.out.println("responseCode : " + responseCode);
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                GetUserInfoRes getUserInfoRes = new GetUserInfoRes(
                        postTokenRes.getAccess_Token(),
                        postTokenRes.getRefresh_Token(),
                        properties.getAsJsonObject().get("nickname").getAsString(),
                        kakao_account.getAsJsonObject().get("email").getAsString());

            return getUserInfoRes;
            }catch (Exception exception) {
                throw new BaseException(INVALID_CODE);
            }
        }
    }


