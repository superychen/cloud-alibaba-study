package com.cqyc.service;

import com.cqyc.dao.SkillOrderRepository;
import com.cqyc.entity.SkillEntity;
import com.cqyc.entity.SkillGood;
import com.cqyc.entity.SkillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

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
    public static final String SKILL_GOODS_List = "SKILL_GOODS_List";
    public static final String SKILL_GOODS_ONLY = "SKILL_GOODS_ONLY";

    public static final String SKILL_STOCK_GOODS_QUEUE = "SKILL_STOCK_GOODS_QUEUE";

    @Autowired
    private MultiThreadOrder multiThreadOrder;

    /**
     * 重复抢单问题分析和实现
     *  一个用户只能有一个排队信息存在
     *  一个用户会存在多个未支付的订单
     */
    @Transactional
    public void add(Long productId, String userId) throws Exception{
        userId = UUID.randomUUID().toString();
        //判断这个用户是否参加过抢单，redis自增命令相当于atomicInteger一样，能保证原子性
        Long time = redisTemplate.boundHashOps(SKILL_GOODS_ONLY).increment(userId, 1L);
        if(time > 1) {
            throw new Exception("重复抢单，不要太贪心！！");
        }

        //开始异步秒杀
        //第一步先封装对象，并且放入redis队列中
        SkillEntity entity = new SkillEntity();
        entity.setProductId(productId);
        entity.setUserId(userId);
        //从左侧插入数据，然后从右侧去除数据
        redisTemplate.boundListOps(SKILL_GOODS_List).leftPush(entity);
        //然后开始异步进行抢单，这里将同步代码放入异步里面
        multiThreadOrder.createOrder();
        System.out.println("同步开始!");


    }


}
