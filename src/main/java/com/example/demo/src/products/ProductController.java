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
