package com.example.demo.src.message;

import com.example.demo.src.message.model.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MessageDetail messageDetail;

    //GET 인증 메세지 전송
    public void sendMessage(int userId, String phonenum, String cerNum) {
        String api_key = "NCSPGW7RIFOGZEBL";
        String api_secret = "BFGGFJBGCYEUU1EEW2GYE3JRZWNZCRWL";
        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phonenum);    // 수신전화번호
        params.put("from", "01036370120");    // 발신전화번호
        params.put("type", "SMS");
        params.put("text", "[soldout] 인증 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            messageDetail = new MessageDetail(userId,phonenum, cerNum);
        } catch (CoolsmsException e) {
        }
    }

    //GET 인증 메세지 확인
    public int checkMessage(int userId, String phonenum, String cerNum) {
        if(messageDetail.getUser_id()==userId&&messageDetail.getCer_num().equals(cerNum)&&messageDetail.getPhone_num().equals(phonenum)){
            return 1;
        }else{
            return 0;
        }

    }
}
