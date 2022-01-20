package com.example.demo.src.magazine;

import com.example.demo.config.BaseException;
import com.example.demo.src.magazine.model.GetMagazineDetailRes;
import com.example.demo.src.magazine.model.GetMagazineRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MagazineProvider {
    private final MagazineDao magazineDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MagazineProvider(MagazineDao magazineDao) {
        this.magazineDao = magazineDao;
    }

    //GET 전체 매거진 조회
    public List<GetMagazineRes> getAllMagazines() throws BaseException {
        try {
            List<GetMagazineRes> getMagazineRes = magazineDao.getAllMagazines();
            return getMagazineRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //GET 타입별 매거진 조회
    public List<GetMagazineRes> getMagazinesByType(String type) throws BaseException {
        if(checkMagazineType(type)){
            throw new BaseException(MAGAZINES_EMPTY_TYPE);
        }
        try {
            List<GetMagazineRes> getMagazineByTypeRes = magazineDao.getMagazinesByType(type);
            return getMagazineByTypeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //GET 매거진 상세회면
    public GetMagazineDetailRes getMagazineDetail(int magazine_id) throws BaseException {
        if(checkMagazineId(magazine_id)){
            throw new BaseException(MAGAZINES_EMPTY_ID);
        }
        try {
            GetMagazineDetailRes getMagazineDetailRes = magazineDao.getMagazineDetail(magazine_id);
            return getMagazineDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkMagazineId(int magazine_id) throws BaseException{
        try{
            boolean result = true;
            if(magazineDao.checkMagazineId(magazine_id)==1){ //존재
                result = false;
            }
            return result;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkMagazineType(String type) throws BaseException{
        try{
            boolean result = true;
            if(magazineDao.checkMagazineType(type)==1){ //존재
                result = false;
            }
            return result;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
