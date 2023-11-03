package com.cqyc.controller;

import com.cqyc.entity.SkillGood;
import com.cqyc.service.SkillGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

/**
 * @author cqyc
 * @create 2023-09-20-18:50
 */
@RestController
@EnableScheduling //开启定时任务
public class ProductController {

    @Autowired
    private SkillGoodService skillGoodService;

    @GetMapping("/product/{productId}")
    public SkillGood getProductInfo(@PathVariable Long productId) {
        System.out.println("调用商品服务");
        return skillGoodService.queryProduct(productId);
    }

    @PostMapping("/product")
    public String update(@RequestBody SkillGood skillGood) {
        skillGoodService.update(skillGood);
        return "更新库存成功！";
    }

}
