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
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST 회원가입
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        if(userProvider.checkId(postUserReq.getId()) ==1){
            throw new BaseException(POST_USERS_EXISTS_ID);
        }
        if(userProvider.checkNickname(postUserReq.getNickname()) ==1){
            throw new BaseException(POST_USERS_EXISTS_NICKNAME);
        }
        if(userProvider.checkPhoneNum(postUserReq.getPhone_num()) ==1){
            throw new BaseException(POST_USERS_EXISTS_PHONENUM);
        }
        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
           int user_id = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(user_id);
            return new PostUserRes(jwt,user_id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // PATCH 유저 삭제
    public void deleteUser(PatchDeleteUserReq patchDeleteUserReq) throws BaseException {
        try{
            int result = userDao.deleteUser(patchDeleteUserReq);
            if(result == 0){
                throw new BaseException(FAIL_DELETE_USER);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // PATCH 닉네임 변경
    public void modifyNickName(PatchUserReq patchUserReq) throws BaseException {
        if(userProvider.checkNickname(patchUserReq.getChangeStr()) ==1){
            throw new BaseException(POST_USERS_EXISTS_NICKNAME);
        }
        try{
            int result = userDao.modifyNickName(patchUserReq);
            if(result == 0){
                throw new BaseException(FAIL_CHANGE_USER);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // PATCH 이메일 변경
    public void modifyEmail(PatchUserReq patchUserReq) throws BaseException {
        if(userProvider.checkNickname(patchUserReq.getChangeStr()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        try{
            int result = userDao.modifyEmail(patchUserReq);
            if(result == 0){
                throw new BaseException(FAIL_CHANGE_USER);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // PATCH 핸드폰번호 변경
    public void modifyPhoneNum(PatchUserReq patchUserReq) throws BaseException {
        if(userProvider.checkNickname(patchUserReq.getChangeStr()) ==1){
            throw new BaseException(POST_USERS_EXISTS_PHONENUM);
        }
        try{
            int result = userDao.modifyPhoneNum(patchUserReq);
            if(result == 0){
                throw new BaseException(FAIL_CHANGE_USER);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // PATCH 비밀번호 변경
    public void modifyPassword(String name, String id, String phonenum,  PatchPasswordReq patchPasswordReq) throws BaseException {
        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchPasswordReq.getPassword());
            patchPasswordReq.setPassword(pwd);
        } catch (Exception ignored) {
           throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int result = userDao.modifyPassword(name, id, phonenum, patchPasswordReq);
            if(result == 0){
                throw new BaseException(FAIL_CHANGE_PASSWORD);
            }
        } catch(Exception exception){
           throw new BaseException(DATABASE_ERROR);
        }
    }
}
