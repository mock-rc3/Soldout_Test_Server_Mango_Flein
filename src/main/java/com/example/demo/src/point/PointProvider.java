package com.example.demo.src.point;

import com.example.demo.config.BaseException;
import com.example.demo.src.magazine.model.GetMagazineDetailRes;
import com.example.demo.src.magazine.model.GetMagazineRes;
import com.example.demo.src.point.model.GetPointHistoryRes;
import com.example.demo.src.point.model.GetPointRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PointProvider {

    private final PointDao pointDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PointProvider(PointDao pointDao, JwtService jwtService) {
        this.pointDao = pointDao;
        this.jwtService = jwtService;
    }
    public List<GetPointHistoryRes> getPontHistory(int user_id) throws BaseException {
       // try {
            List<GetPointHistoryRes> getPointHistoryRes = pointDao.getPontHistory(user_id);
            return getPointHistoryRes;
      //  } catch (Exception exception) {
        //    throw new BaseException(DATABASE_ERROR);
       // }
    }
    public List<GetPointHistoryRes> getPontHistoryByType(int user_id, String type) throws BaseException {
        //try {
            List<GetPointHistoryRes> getPointHistoryRes = pointDao.getPontHistoryByType(user_id,type);
            return getPointHistoryRes;
       // } catch (Exception exception) {
        //    throw new BaseException(DATABASE_ERROR);
        //}
    }
    public List<GetPointHistoryRes> getCancelPontHistory(int user_id) throws BaseException {
       // try {
            List<GetPointHistoryRes> getPointHistoryRes = pointDao.getCancelPontHistory(user_id);
            return getPointHistoryRes;
       // } catch (Exception exception) {
       //     throw new BaseException(DATABASE_ERROR);
       // }
    }
    public GetPointRes getPoint(int user_id) throws BaseException {

        try {
            GetPointRes getPointRes = pointDao.getPoint(user_id);
            return getPointRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
