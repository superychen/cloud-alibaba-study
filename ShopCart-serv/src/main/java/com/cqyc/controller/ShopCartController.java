package com.cqyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cqyc
 * @create 2023-09-20-18:59
 */
@RestController
public class ShopCartController {

    @GetMapping("/shopCart/create")
    private String createShopCart(@RequestParam("userId") String userId, @RequestParam("productId") String productId) {
        return "加入购物车成功" + userId + "  " + productId;
    }

}
