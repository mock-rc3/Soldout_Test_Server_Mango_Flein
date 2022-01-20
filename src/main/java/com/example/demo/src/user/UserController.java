package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;
import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 정보 조회 API
     * [GET] /users/:userId
     * @param user_id
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int user_id) {
        try {
            GetUserRes getUserRes = userProvider.getUser(user_id);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @param postUserReq
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // id 미입력
        if (postUserReq.getId() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_ID);
        }
        // id 정규표현
        if (!IdPattern(postUserReq.getId())) {
            return new BaseResponse<>(POST_USERS_INVALID_ID);
        }
        // pw 미입력
        if (postUserReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        // pw 정규표현
        if (!PwPattern(postUserReq.getPassword())) {
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }
        // phoneNum 미입력
        if (postUserReq.getPhone_num() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUM);
        }
        // email 미입력
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        // email 정규표현
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        // name 미입력
        if (postUserReq.getName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        // nickname 미입력
        if (postUserReq.getNickname() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        //닉네임 길이 제한
        if(!nickNamePattern(postUserReq.getNickname())){
            return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 비밀번호 정규표현 점검
     * @param str
     * @return t : 포함, f : 미포함
     */
    public boolean PwPattern(String str) {
        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";
        boolean result = Pattern.matches(pattern, str);
        return result;
    }

    /**
     * 아이디 정규표현 점검
     * @param str
     * @return t : 포함, f : 미포함
     */
    public boolean IdPattern(String str) {
        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{5,11}$";
        boolean result = Pattern.matches(pattern, str);
        return result;
    }

    /**
     * 닉네임 길이 점검
     * @param str
     * @return t , f
     */
    public boolean nickNamePattern(String str) {
        boolean result = false;
        if(str.length()<9&&str.length()>2){
            result = true;
        }
        return result;
    }

    /**
     * 로그인 API
     * [POST] /users/login
     * @param postLoginReq
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        // id 미입력
        if (postLoginReq.getId() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_ID);
        }
        // pw 미입력
        if (postLoginReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        try {
            // 탈퇴한 회원
            if (userProvider.checkStatus(postLoginReq.getId())==0) {
                return new BaseResponse<>(USERS_STATUS_DELETE);
            }
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 삭제 API
     * [PATCH] /users/:userId/delete
     * @param user_id
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}/delete")
    public BaseResponse<String> deleteUser(@PathVariable("userId") int user_id) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (user_id != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchDeleteUserReq patchDeleteUseReq = new PatchDeleteUserReq(user_id, 0);
            userService.deleteUser(patchDeleteUseReq);
            String result = "삭제 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 아이디 찾기 API
     * [GET] /users/:name/:phonenum
     * @param name 유저 이름
     * @param phonenum 유저 핸드폰번호
     * @return BaseResponse<String>
     */
    @ResponseBody
    @GetMapping("/{name}/{phonenum}")
    public BaseResponse<String> findId(@PathVariable("name") String name, @PathVariable("phonenum") String phonenum) {
        try {
            GetFindIdRes getFindIdRes = userProvider.findId(name, phonenum);
            String result = getFindIdRes.getId();
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 비밀번호 변경 API
     * [PATCH] /users/:name/:id/:phonenum
     * @param name 유저 이름
     * @param id 유저 아이디
     * @param phonenum 유저 핸드폰번호
     * @param patchPasswordReq
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{name}/{id}/{phonenum}")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> modifyPassword(@PathVariable("name") String name, @PathVariable("id") String id, @PathVariable("phonenum") String phonenum, @RequestBody PatchPasswordReq patchPasswordReq) {
        try {
            // pw 정규표현
            if (!PwPattern(patchPasswordReq.getPassword())) {
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
            userService.modifyPassword(name, id, phonenum, patchPasswordReq);
            String result = "변경 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 닉네임 변경 API
     * [PATCH] /users/:userId/nickname
     * @param user_id 유저 아이디
     * @param user
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}/nickname")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> modifyNickName(@PathVariable("userId") int user_id, @RequestBody User user) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (user_id != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(!nickNamePattern(user.getNickname())){
                return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
            }
            PatchUserReq patchUserReq = new PatchUserReq(user_id, user.getNickname());
            userService.modifyNickName(patchUserReq);
            String result = user.getNickname() + "으로 닉네임 변경성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 이메일 변경 API
     * [PATCH] /users/:userId/phonenum
     * @param user_id 유저 아이디
     * @param user
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}/email")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> modifyEmail(@PathVariable("userId") int user_id, @RequestBody User user) {
        // email 정규표현
        if (!isRegexEmail(user.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            int userIdByJwt = jwtService.getUserId();
            if (user_id != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserReq patchUserReq = new PatchUserReq(user_id, user.getEmail());
            userService.modifyEmail(patchUserReq);
            String result = user.getEmail() + "으로 이메일 변경성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 핸드폰번호 변경 API
     * [PATCH] /users/:userId/phonenum
     * @param user_id 유저 아이디
     * @param user
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}/phonenum")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> modifyPhoneNum(@PathVariable("userId") int user_id, @RequestBody User user) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (user_id != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserReq patchUserReq = new PatchUserReq(user_id, user.getPhone_num());
            userService.modifyPhoneNum(patchUserReq);
            String result = user.getPhone_num() + "으로 핸드폰번호 변경성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}




