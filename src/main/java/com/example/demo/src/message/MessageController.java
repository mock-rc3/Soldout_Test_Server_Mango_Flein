package com.example.demo.src.message;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.message.model.GetMessageReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/messages")
public class MessageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MessageService messageService;
    @Autowired
    private final JwtService jwtService;

    public MessageController(MessageService messageService, JwtService jwtService) {
        this.messageService = messageService;
        this.jwtService = jwtService;
    }

    /**
     * 핸드폰 인증 메세지 전송 api
     * [GET] /messages/:userId
     * @return cerNum
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<String> sendMessage(@PathVariable("userId") int user_id, @RequestBody String phonenum) {
        try {
            int userId = jwtService.getUserId();
            if (user_id != userId) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            Random rand = new Random();
            String cerNum = "";
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(rand.nextInt(10));
                cerNum += ran;
            }
            messageService.sendMessage(userId, phonenum, cerNum);
            return new BaseResponse<>(cerNum);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 핸드폰 인증 메세지 인증 api
     * [GET] /messages/check/:userId
     * @return 인증 결과
     */
    @ResponseBody
    @GetMapping("/check/{userId}")
    public BaseResponse<String> checkMessage(@PathVariable("userId") int user_id, @RequestBody GetMessageReq getMessageReq) {
        try {
            int userId = jwtService.getUserId();
            if (user_id != userId) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            int result = messageService.checkMessage(user_id, getMessageReq.getPhone_num(), getMessageReq.getCer_num());
            if(result == 1) {
                return new BaseResponse<>("인증 성공");
            }else{
                return new BaseResponse<>("인증 실패");
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
