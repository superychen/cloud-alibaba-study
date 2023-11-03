package com.cqyc.service;

import com.cqyc.entity.SkillGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author cqyc
 * @create 2023-11-02-18:02
 */
@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    public SkillGood queryByProductId(Long productId) {
        return restTemplate.getForObject("http://product-serv/product/" + productId, SkillGood.class);
    }

    public void update(SkillGood skillGood) {
        ResponseEntity<String> result = restTemplate.postForEntity("http://product-serv/product/", skillGood, String.class);
        System.out.println(result.getBody());
    }

}
