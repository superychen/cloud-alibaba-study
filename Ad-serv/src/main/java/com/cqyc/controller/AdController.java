package com.cqyc.controller;

import com.cqyc.entity.AdEntity;
import com.cqyc.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cqyc
 * @create 2023-11-15-16:59
 */
@RestController
public class AdController {

    @Autowired
    private AdService adService;

    @GetMapping("/ad/getAdList")
    @ResponseBody
    public List<AdEntity> getAdList() {
        return adService.getADList();
    }

}
