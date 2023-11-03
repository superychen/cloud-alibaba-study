package com.cqyc.controller;

import com.cqyc.service.SkillGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cqyc
 * @create 2023-11-03-13:59
 */
@RestController
public class SkillController {

    @Autowired
    private SkillGoodService skillGoodService;

    @GetMapping("/skill")
    public String skill(String userId, Long productId) {
        try {
            skillGoodService.add(productId, userId);
            return "秒杀成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
