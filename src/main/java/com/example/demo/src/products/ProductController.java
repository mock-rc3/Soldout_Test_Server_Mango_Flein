package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.products.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class  ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final ProductProvider productprovider;


    public ProductController(ProductProvider productProvider,ProductService productService, JwtService jwtService)
    {
        this.productprovider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }

    /**
     * 신규 발매 상품 조회 API
     * [GET] /products/new
     *
     * @return BaseResponse<List < GetNewRes>>
     */
    @ResponseBody
    @GetMapping("/new")
    public BaseResponse<List<GetNewRes>> getNewProduct() {
        try{
            // Get Users
            List<GetNewRes> getNewRes = productprovider.getNewProduct();
            return new BaseResponse<>(getNewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 랭킹 조회 API
     * [GET] /products/rank
     *
     * @return BaseResponse<List < GetRankRes>>
     */
    @ResponseBody
    @GetMapping("/rank")
    public BaseResponse<List<GetRankRes>> getRankProduct(@RequestParam(value="category",required=false) String category) {
        try{

            if(category == null){
                List<GetRankRes> getRankRes = productprovider.getRankProduct();
                return new BaseResponse<>(getRankRes);
            }else{
                List<GetRankRes> getRankRes = productprovider.getRankProductFilter(category);
                return new BaseResponse<>(getRankRes);
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 메인페이지 배너, 특정 검색어 제품  조회 API
     * [GET] /products/goods
     *
     * @return BaseResponse<List < GetGoodsRes>>
     */
    @ResponseBody
    @GetMapping("/goods")
    public BaseResponse<List<GetGoodsRes>> getGoodsList(@RequestParam(value="search",required=false) String search) {
        try{

            if(search == null){
                List<GetGoodsRes> getGoodsRes = productprovider.getGoodsList();
                return new BaseResponse<>(getGoodsRes);
            }else{
                List<GetGoodsRes> getGoodsRes = productprovider.getGoodsListFilter(search);
                return new BaseResponse<>(getGoodsRes);
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 새로운 즉시 판매가 상품 조회 API
     * [GET] /products/newsell
     *
     * @return BaseResponse<List < GetGoodsRes>>
     */
    @ResponseBody
    @GetMapping("/newsell")
    public BaseResponse<List<GetGoodsRes>> getNewSellList() {
        try{
            List<GetGoodsRes> getGoodsRes = productprovider.getNewSellList();
            return new BaseResponse<>(getGoodsRes);


        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 날짜별 발매 상품 API
     * [GET] /products/day
     * 날짜별 발매 상품 API (로그인 한경우)
     * [GET] /products/day/:userId
     *
     * @return BaseResponse<List < GetDayAlarmRes>>
     */
    @ResponseBody
    @GetMapping("/day")
    public BaseResponse<List<GetDayAlarmRes>> getDayAlarm(@RequestParam(value="userId",required=false,defaultValue = "0") int userId ) {
        try{
            if(userId == 0){
                List<GetDayAlarmRes> getDayAlarmRes = productprovider.getDayAlarm();
                return new BaseResponse<>(getDayAlarmRes);
            }else{
                List<GetDayAlarmRes> getDayAlarmRes = productprovider.getDayAlarmFilter(userId);
                return new BaseResponse<>(getDayAlarmRes);
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 연관 추천 상품 조회 API
     * [GET] /products/brand/:brandName
     *
     * @return BaseResponse<List<GetRelateRecommendRes>>
     */
    @ResponseBody
    @GetMapping("/brand/{brandName}")
    public BaseResponse<List<GetRelateRecommendRes>> getRelateRecommend(@PathVariable("brandName") String brandName) {
        try{

            List<GetRelateRecommendRes> getRelateRecommendRes = productprovider.getRelateRecommend(brandName);
            return new BaseResponse<>(getRelateRecommendRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상세페이지 상품 이미지 조회 API
     * [GET] /products/detailimage/:productId
     *
     * @return BaseResponse<List<GetProductImageRes>>
     */
    @ResponseBody
    @GetMapping("/detailimage/{productId}")
    public BaseResponse<List<GetProductImageRes>> getDetailProductImage(@PathVariable("productId") int productId) {
        try{

            List<GetProductImageRes> getProductImageRes = productprovider.getDetailProductImage(productId);
            return new BaseResponse<>(getProductImageRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 상단 정보 조회 API
     * [GET] /products/topinform/:productId
     *
     * @return BaseResponse<List<GetTopInforRes>>
     */
    @ResponseBody
    @GetMapping("/topinform/{productId}")
    public BaseResponse<List<GetTopInforRes>> getDetailTopInformation(@PathVariable("productId") int productId) {
        try{

            List<GetTopInforRes> getTopInforRes = productprovider.getDetailTopInformation(productId);
            return new BaseResponse<>(getTopInforRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 최근거래 조회 API
     * [GET] /products/recent/:productId
     *
     * @return BaseResponse<List<GetRecentRes>>
     */
    @ResponseBody
    @GetMapping("/recent/{productId}")
    public BaseResponse<List<GetRecentRes>> getDetailRecent(@PathVariable("productId") int productId) {
        try{

            List<GetRecentRes> getRecentRes = productprovider.getDetailRecent(productId);
            return new BaseResponse<>(getRecentRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 입찰 현황 조회 API
     * [GET] /products/deal/:productId/:types
     *
     * @return BaseResponse<List<getDealRes>>
     */
    @ResponseBody
    @GetMapping("/deal/{productId}/{types}")
    public BaseResponse<List<GetDealRes>> getDetailDeal(@PathVariable("productId") int productId,@PathVariable("types") String types) {
        try{

            List<GetDealRes> getDealRes = productprovider.getDetailDeal(productId,types);
            return new BaseResponse<>(getDealRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 사이즈별 거래가 조회 API
     * [GET] /products/sizes/:productId/:types
     *
     * @return BaseResponse<List<GetSizePriceRes>>
     */
    @ResponseBody
    @GetMapping("/sizes/{productId}/{types}")
    public BaseResponse<List<GetSizePriceRes>> getSizeTransPrice(@PathVariable("productId") int productId, @PathVariable("types") String types) {
        try{
            List<GetSizePriceRes> getSizePriceRes = productprovider.getSizeTransPrice(productId,types);
            return new BaseResponse<>(getSizePriceRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 정보, 구매처 조회 API
     * [GET] /products/information/:productId
     *
     * @return BaseResponse<List<GetInformationRes>>
     */
    @ResponseBody
    @GetMapping("/information/{productId}")
    public BaseResponse<List<GetInformationRes>> getDetailInformation(@PathVariable("productId") int productId) {
        try{

            List<GetInformationRes> getInformationRes = productprovider.getDetailInformation(productId);
            return new BaseResponse<>(getInformationRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 검색 페이지 조회 API
     * [GET] /products/search
     *
     * @return BaseResponse<List<GetSearchRes>>
     */

    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetSearchRes>> getSearchProduct(@RequestParam(value="category",required=false) String category,@RequestParam(value="keyword",required=false) String keyword) {
        try{
            // Get Users
            if(category == null && keyword == null ){
                List<GetSearchRes> getSearchRes = productprovider.getSearchProduct();
                return new BaseResponse<>(getSearchRes);
            }
            else if(category == null && keyword != null ){
                List<GetSearchRes> getRankRes = productprovider.getSearchKeywordFilter(keyword);
                return new BaseResponse<>(getRankRes);
            }
            else if(category != null && keyword == null ){
                List<GetSearchRes> getRankRes = productprovider.getSearchCategoryFilter(category);
                return new BaseResponse<>(getRankRes);
            }else{
                List<GetSearchRes> getRankRes = productprovider.getSearchFilter(category,keyword);
                return new BaseResponse<>(getRankRes);
            }

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 발매 예정 API
     * [GET] /products/release
     *
     * @return BaseResponse<List<GetReleaseRes>>
     */

    @ResponseBody
    @GetMapping("/release")
    public BaseResponse<List<GetReleaseRes>> getReleaseProduct() {
        try{
            // Get Users
            List<GetReleaseRes> getReleaseRes = productprovider.getReleaseProduct();
            return new BaseResponse<>(getReleaseRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 핫이슈 인기 상품 조회 API
     * [GET] /products/release
     *
     * @return BaseResponse<List<GetHotissueRes>>
     */
    @ResponseBody
    @GetMapping("/hotissue")
    public BaseResponse<List<GetHotissueRes>> getHotissue() {
        try{
            // Get Users
            List<GetHotissueRes> getHotissueRes = productprovider.getHotissue();
            return new BaseResponse<>(getHotissueRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
