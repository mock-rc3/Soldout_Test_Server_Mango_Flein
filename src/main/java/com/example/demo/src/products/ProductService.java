package com.example.demo.src.products;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;
    private final JwtService jwtService;
    private final ProductProvider productprovider;

    @Autowired
    public ProductService(ProductDao productDao,ProductProvider productProvider, JwtService jwtService)
    {
        this.productprovider = productProvider;
        this.productDao = productDao;
        this.jwtService = jwtService;
    }




}
