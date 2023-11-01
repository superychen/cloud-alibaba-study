package com.cqyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cqyc
 * @create 2023-09-20-18:50
 */
@RestController
public class ProductController {

    @GetMapping("/product/{productId}")
    public String getProductInfo(@PathVariable String productId) {
        return "product-serv" + productId;
    }

}
