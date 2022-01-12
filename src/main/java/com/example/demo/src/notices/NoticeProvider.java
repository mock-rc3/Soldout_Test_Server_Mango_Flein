package com.example.demo.src.notices;

import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeProvider {

    private final NoticeDao noticeDao;
    private final JwtService jwtService;

    @Autowired
    public NoticeProvider(NoticeDao noticeDao, JwtService jwtService){
        this.noticeDao = noticeDao;
        this.jwtService = jwtService;
    }

}
