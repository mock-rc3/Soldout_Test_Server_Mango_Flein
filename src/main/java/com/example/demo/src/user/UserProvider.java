package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    // GET 회원 정보 조회
    public GetUserRes getUser(int user_id) throws BaseException {
        User user = userDao.getUserId(user_id);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if(userDao.checkUserId(user_id) ==0){
            throw new BaseException(USERS_EMPTY_USERID);
        }
        try {
            GetUserRes getUserRes = userDao.getUser(user_id);
            getUserRes.setPassword(password);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // POST 로그인
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        User user = userDao.getPwd(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(postLoginReq.getPassword().equals(password)){
            int user_id = userDao.getPwd(postLoginReq).getUser_id();
            String jwt = jwtService.createJwt(user_id);
            return new PostLoginRes(user_id,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    // GET 아이디 찾기
    public GetFindIdRes findId(String name, String phoneNum) throws BaseException {
        try {
            GetFindIdRes getFindIdRes= userDao.findId(name,phoneNum);
            return getFindIdRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkId(String id) throws BaseException{
        try{
            return userDao.checkId(id);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickname(String nickname) throws BaseException{
        try{
            return userDao.checkNickname(nickname);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkPhoneNum(String nickname) throws BaseException{
        try{
            return userDao.checkPhoneNum(nickname);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkStatus(String id) throws BaseException {
        try {
            return userDao.checkStatus(id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
