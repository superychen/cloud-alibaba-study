package com.cqyc.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author cqyc
 * @create 2023-10-24-16:38
 */
@RestController
public class TestController {

    @GetMapping("/list")
    public String test() {
        return "sentinel list";
    }

    @GetMapping("/list1")
    public String test1() {
        return "sentinel list1";
    }

    @GetMapping("/hot")
    @SentinelResource("host")
    public String hot(@RequestParam("productId") String productId, @RequestParam("userId") String userId) {
        return "productId: " + productId + " userId: " + userId;
    }

    public static void main(String[] args) throws InterruptedException {
        while(true) {
            RestTemplate restTemplate = new RestTemplate();
            String resStr = restTemplate.getForObject("http://localhost:10086/list1", String.class);
            System.out.println("resStr = " + resStr);
            Thread.sleep(500);
        }
    }

}
