package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.src.wish.model.PostWishReq;
import com.example.demo.src.wish.model.PostWishRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.FAIL_DELETE_ADDRESS;

@Service
public class WishService {
    private final  WishDao wishDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WishService(WishDao wishDao) {
        this.wishDao = wishDao;
    }

    // POST 찜 추가
    public PostWishRes createWish(int userId, PostWishReq postWishReq) throws BaseException {
        int newWish = wishDao.createWish(userId, postWishReq);
        return new PostWishRes(newWish);
    }

    // PATCH 찜 삭제
    public void deleteWish(int favoriteId) throws BaseException {
        try {
            int result = wishDao.deleteWish(favoriteId);
            if (result == 0) {
                throw new BaseException(FAIL_DELETE_ADDRESS);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
