package com.cqyc.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author cqyc
 * @create 2023-09-20-18:47
 */
@RestController
@RefreshScope
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

//    @Value("${testStr}")
//    private String testStr;
//
//    @Value("${redisStr}")
//    private String redisStr;
//
//    @Value("${mq.url}")
//    private String mqUrl;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/order/create")
    public String createOrder(@RequestParam("userId") String userId, @RequestParam("productId") String productId) {
        return "1111";
    }

    @GetMapping("/order")
    public String getDiscovery() {
        List<ServiceInstance> instances = discoveryClient.getInstances("order-serv");
        StringBuilder res = new StringBuilder();
        for (ServiceInstance instance : instances) {
            res.append(instance.getHost()).append(":").append(instance.getPort());
        }
        return res.toString();
    }

    @GetMapping("/config/str")
    public String getConfigStr() {
//        return testStr + " ddd: " + redisStr + " mqUrl: " + mqUrl;
        return "";
    }

    public static void main(String[] args) {
        //第一期开奖号码
        List<Integer> compareBlueInts = Lists.newArrayList(6,7,14,20,26,32);
        int redInt = 1;
        Long num = 0L;
        Map<String, Integer> map = caiPiaoCompute(compareBlueInts, redInt, num);
        for (int i = 0; i < 10; i++) {
            buyCaiPiao(map.get("red"), map.get("blue"));
        }
    }


    public static void buyCaiPiao(Integer num1, Integer num2) {
        List<Integer> resInts = new ArrayList<>();
        Random random = new Random();
        boolean flag = true;
        int firstNum = 0;
        while (flag) {
            int resInt = random.nextInt(34);
            if(resInt != 0 && !resInts.contains(resInt)) {
                resInts.add(resInt);
            }
            if (resInts.size() == 6) {
                if(firstNum == num1) {
                    resInts.sort(Comparator.comparingInt(o -> o));
                    flag = false;
                } else {
                    firstNum++;
                    resInts.clear();
                }
            }
        }
//        System.out.println(StringUtils.join(resInts, " "));
        int secondNum = 0;
        while (true) {
            int i = random.nextInt(16);
            if(i != 0) {
                if(secondNum == num2) {
                    resInts.add(i);
                    break;
                } else {
                    secondNum++;
                }
            }
        }
        StringBuilder resStr = new StringBuilder();
        for (Integer resInt : resInts) {
            String str =resInt < 10 ? "0" + resInt : String.valueOf(resInt);
            resStr.append(str).append(" ");
        }
        System.out.println(resStr);
    }

    public static Map<String, Integer> caiPiaoCompute(List<Integer> compareBlueInts, Integer redInt, Long num) {
        Map<String, Integer> resMap = new HashMap<>();

        List<Integer> resInts = new ArrayList<>();
        Random random = new Random();
        boolean flag = true;
        while (flag) {
            int resInt = random.nextInt(34);
            if(resInt != 0 && !resInts.contains(resInt)) {
                resInts.add(resInt);
            }
            if (resInts.size() == 6) {
                num = num + 1L;
                resInts.sort(Comparator.comparingInt(o -> o));
                if(!Objects.equals(resInts, compareBlueInts)) {
                    resInts.clear();
                } else {
                    flag = false;
                }
            }
        }
        System.out.println("计算次数： " + num);
        System.out.println(StringUtils.join(resInts, " "));

        int num2 = 0;
        while (true) {
            int i = random.nextInt(17);
            if(i != 0) {
                if(redInt == i) {
                    resInts.add(i);
                    break;
                } else {
                    num2++;
                }
            }
        }
        System.out.println("计算次数： " + num2);
        System.out.println(StringUtils.join(resInts, " "));
        resMap.put("red", num.intValue());
        resMap.put("blue", num2);
        return resMap;
    }



}
