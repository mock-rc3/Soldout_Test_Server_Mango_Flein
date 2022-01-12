package com.example.demo.src.products;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.products.model.GetNewRes;
import com.example.demo.src.products.model.GetRankRes;
import com.example.demo.src.products.model.GetReleaseRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
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
    public BaseResponse<List<GetRankRes>> getRankProduct() {
        try{
            // Get Users
            List<GetRankRes> getRankRes = productprovider.getRankProduct();
            return new BaseResponse<>(getRankRes);
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
}