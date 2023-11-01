package com.cqyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cqyc
 * @create 2023-09-20-18:55
 */
@RestController
public class UserController {

    @GetMapping("/user/{userId}")
    public String getUserInfo(@PathVariable String userId) {
        return "user-serv" + userId;
    }

}
