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
            // Get Users
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
