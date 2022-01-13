package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.model.GetNewRes;
import com.example.demo.src.products.model.GetRankRes;
import com.example.demo.src.products.model.GetReleaseRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ProductProvider {

    private final ProductDao productDao;
    private final JwtService jwtService;

    @Autowired
    public ProductProvider(ProductDao productDao, JwtService jwtService){
        this.productDao = productDao;
        this.jwtService = jwtService;
    }

    public List<GetNewRes> getNewProduct() throws BaseException {
        try{
            List<GetNewRes> getNewRes = productDao.getNewProduct();
            return getNewRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetRankRes> getRankProductFilter(String category) throws BaseException {
        try{
            List<GetRankRes> getRankRes = productDao.getRankProductFilter(category);
            return getRankRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetRankRes> getRankProduct() throws BaseException {
        try{
            List<GetRankRes> getRankRes = productDao.getRankProduct();
            return getRankRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetReleaseRes> getReleaseProduct() throws BaseException {
        try{
            List<GetReleaseRes> getReleaseRes = productDao.getReleaseProduct();
            return getReleaseRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
