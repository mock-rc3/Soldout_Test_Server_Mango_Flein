package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.src.products.model.*;
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

    public List<GetRelateRecommendRes> getRelateRecommend( String brandName) throws BaseException {
        try{
            List<GetRelateRecommendRes> getRelateRecommendRes = productDao.getRelateRecommend(brandName );
            return getRelateRecommendRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetTopInforRes> getDetailTopInformation(int productId) throws BaseException {
        try{
            List<GetTopInforRes> getTopInforRes = productDao.getDetailTopInformation(productId);
            return getTopInforRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductImageRes> getDetailProductImage(int productId) throws BaseException {
        try{
            List<GetProductImageRes> getProductImageRes = productDao.getDetailProductImage(productId);
            return getProductImageRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRecentRes> getDetailRecent(int productId) throws BaseException {
        try{
            List<GetRecentRes> getRecentRes = productDao.getDetailRecent(productId);
            return getRecentRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDealRes> getDetailDeal(int productId,String types) throws BaseException {
        try{
            List<GetDealRes> getDealRes = productDao.getDetailDeal(productId,types);
            return getDealRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSizePrice> getSizeTransPrice(int productId,String types) throws BaseException {
        try{
            List<GetSizePrice> getSizePrice = productDao.getSizeTransPrice(productId,types);
            return getSizePrice;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetInformationRes> getDetailInformation(int productId) throws BaseException {
        try{
            List<GetInformationRes> getInformationRes = productDao.getDetailInformation(productId);
            return getInformationRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetSearchRes> getSearchProduct() throws BaseException {
        try{
            List<GetSearchRes> getSearchProduct = productDao.getSearchProduct();
            return getSearchProduct;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSearchRes> getSearchKeywordFilter(String keyword) throws BaseException {
        try{
            List<GetSearchRes> getSearchProduct = productDao.getSearchKeywordFilter(keyword);
            return getSearchProduct;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSearchRes> getSearchCategoryFilter(String category) throws BaseException {
        try{
            List<GetSearchRes> getSearchProduct = productDao.getSearchCategoryFilter(category);
            return getSearchProduct;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSearchRes> getSearchFilter(String category,String keyword) throws BaseException {
        try{
            List<GetSearchRes> getSearchProduct = productDao.getSearchFilter(category,keyword);
            return getSearchProduct;
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

    public List<GetHotissueRes> getHotissue() throws BaseException {
        try{
            List<GetHotissueRes> getHotissueRes = productDao.getHotissue();
            return getHotissueRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
