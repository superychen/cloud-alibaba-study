package com.cqyc.service;

import com.cqyc.dao.SkillOrderRepository;
import com.cqyc.entity.SkillGood;
import com.cqyc.entity.SkillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author cqyc
 * @create 2023-11-03-09:18
 */
@Service
public class SkillGoodService {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkillOrderRepository skillOrderRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String SKILL_GOODS_PHONE = "SKILL_GOODS_PHONE";

    @Transactional
    public void add(Long productId, String userId) throws Exception{
        SkillGood skillGood = productService.queryByProductId(productId);
        if(skillGood == null) {
            throw new Exception("商品已经被抢光啦");
        }
        if(skillGood.getStockCount() > 0) {
            SkillOrder skillOrder = new SkillOrder();
            skillOrder.setSkillId(productId);
            skillOrder.setMoney(skillGood.getCostPrice());
            skillOrder.setUserId(userId);
            skillOrder.setCreateTime(new Date());
            skillOrder.setPayTime(new Date());
            skillOrder.setStatus("0");
            skillOrderRepository.save(skillOrder);
            skillGood.setStockCount(skillGood.getStockCount() - 1);
            redisTemplate.boundHashOps(SKILL_GOODS_PHONE).put(skillGood.getId(), skillGood);
            System.out.println("成功秒杀 剩余库存： " + skillGood.getStockCount());
        }
        if(skillGood.getStockCount() <= 0) {
            System.out.println("库存已经是负数了：" + skillGood.getStockCount());
            redisTemplate.boundHashOps(SKILL_GOODS_PHONE).delete(skillGood.getId());
            productService.update(skillGood);
        }

    }


}
