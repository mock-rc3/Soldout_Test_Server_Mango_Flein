package com.example.demo.src.notices;

import com.example.demo.config.BaseException;
import com.example.demo.src.notices.model.PostNoticeReq;
import com.example.demo.src.notices.model.PostNoticeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class NoticeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NoticeDao noticeDao;
    private final JwtService jwtService;
    private final  NoticeProvider noticeProvider;

    @Autowired
    public NoticeService (NoticeDao noticeDao, NoticeProvider noticeProvider, JwtService jwtService){
        this.noticeProvider = noticeProvider;
        this.noticeDao = noticeDao;
        this.jwtService = jwtService;
    }

    //POST
    public PostNoticeRes createNotice(PostNoticeReq postNoticeReq) throws BaseException {
        try{
            int userIdx = noticeDao.createNotice(postNoticeReq);
            return new PostNoticeRes(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
