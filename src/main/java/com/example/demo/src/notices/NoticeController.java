package com.example.demo.src.notices;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.notices.model.PostNoticeReq;
import com.example.demo.src.notices.model.PostNoticeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notices")
public class NoticeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final NoticeProvider noticeProvider;
    @Autowired
    private final NoticeService noticeService;
    @Autowired
    private final JwtService jwtService;

    public NoticeController(NoticeProvider noticeProvider, NoticeService noticeService, JwtService jwtService){
        this.noticeProvider = noticeProvider;
        this.noticeService = noticeService;
        this.jwtService = jwtService;
    }

    /**
     * 알림 등록 API
     * [POST]
     * @return BaseResponse<PostNoticeRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostNoticeRes> createNotice(@RequestBody PostNoticeReq postNoticeReq) {
        try{
            PostNoticeRes postContentClickRes = noticeService.createNotice(postNoticeReq);
            return new BaseResponse<>(postContentClickRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
