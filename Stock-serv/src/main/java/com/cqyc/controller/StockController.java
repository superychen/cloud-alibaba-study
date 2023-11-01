package com.cqyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cqyc
 * @create 2023-09-20-18:57
 */
@RestController
public class StockController {

    @GetMapping("/stock/product")
    private String stockProduct(@RequestParam("productId") String productId) {
        return "减库存" + productId;
    }

}
