package com.example.demo.src.wish;

import com.example.demo.config.BaseException;
import com.example.demo.src.wish.model.GetWishRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class WishProvider {
    private final  WishDao wishDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WishProvider(WishDao wishDao) {
        this.wishDao = wishDao;
    }

    public List<GetWishRes> getWish(int userId) throws BaseException {
       try {
            List<GetWishRes> getWishRes = wishDao.getWish(userId);
            return getWishRes;
       } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
       }
    }

    public List<GetSizeRes> getSize(int user_id, int product_id) throws BaseException {
        try {
            List<GetSizeRes> getSizeRes = wishDao.getSize(user_id,product_id);
            return getSizeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
