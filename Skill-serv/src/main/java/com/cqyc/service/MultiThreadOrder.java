package com.cqyc.service;

import com.cqyc.dao.SkillOrderRepository;
import com.cqyc.entity.SkillEntity;
import com.cqyc.entity.SkillGood;
import com.cqyc.entity.SkillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author cqyc
 * @create 2023-11-03-14:42
 * 异步多线程类创建订单
 */
@Component
public class MultiThreadOrder {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkillOrderRepository skillOrderRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Async
    public void createOrder() throws Exception {

        System.out.println("开始异步抢单！");
        SkillEntity skillEntity = (SkillEntity) redisTemplate.boundListOps(SkillGoodService.SKILL_GOODS_List).rightPop();
        if(skillEntity == null) {
            return;
        }
        System.out.println("结束异步抢单");

        SkillGood skillGood = productService.queryByProductId(skillEntity.getProductId());
        if(skillGood == null) {
            throw new Exception("商品已经被抢光啦");
        }

        Long stockId = (Long) redisTemplate.boundListOps(SkillGoodService.SKILL_STOCK_GOODS_QUEUE + skillEntity.getProductId()).rightPop();
        if(stockId == null) {
            System.out.println("该商品已被秒杀完毕！");
            redisTemplate.boundHashOps(SkillGoodService.SKILL_GOODS_ONLY).delete(skillEntity.getUserId());
            redisTemplate.boundHashOps(SkillGoodService.SKILL_GOODS_PHONE).delete(skillGood.getId());
            skillGood.setStockCount(0);
            productService.update(skillGood);
            return;
        }
        //这里不需要在判断库存数量大于0了，因为在内存中取出的库存数量一定是大于0的
        SkillOrder skillOrder = new SkillOrder();
        skillOrder.setSkillId(skillEntity.getProductId());
        skillOrder.setMoney(skillGood.getCostPrice());
        skillOrder.setUserId(skillEntity.getUserId());
        skillOrder.setCreateTime(new Date());
        skillOrder.setPayTime(new Date());
        skillOrder.setStatus("0");
        skillOrderRepository.save(skillOrder);
        System.out.println("结束异步抢单！！");
    }

}
