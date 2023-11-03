package com.cqyc.service;

import com.cqyc.dao.SkillGoodRepository;
import com.cqyc.entity.SkillGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author cqyc
 * @create 2023-11-02-16:19
 */
@Service
public class SkillGoodService {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String SKILL_GOODS_PHONE = "SKILL_GOODS_PHONE";

    @Autowired
    private SkillGoodRepository skillGoodRepository;

    /**
     * 每五秒执行一次，将需要参与秒杀的商品列表加载到内存中
     * 秒杀的第一步：动态加载商品数据到缓存中
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void prepareGood() {
        System.out.println("开始加载商品");
        //获取所有已经在内存当中的商品id列表
        Set<Long> set = redisTemplate.boundHashOps(SKILL_GOODS_PHONE).keys();
        List<Long> ids = new ArrayList<>();

        for (Long id : set) {
            ids.add(id);
        }
        List<SkillGood> list = null;
        //只查询出不在内存当中的商品信息，并加载到内存
        if(CollectionUtils.isEmpty(ids)) {
            list = skillGoodRepository.findSkillAll();
        } else {
            list = skillGoodRepository.findSkill(ids);
        }
        if(!CollectionUtils.isEmpty(list)) {
            for (SkillGood skillGood : list) {
                redisTemplate.boundHashOps(SKILL_GOODS_PHONE).put(skillGood.getId(), skillGood);
            }
        }

        //查询当前缓存中所有的商品信息,这里只是打印出来看一下
        Set keys = redisTemplate.boundHashOps(SKILL_GOODS_PHONE).keys();
        for (Object key : keys) {
            SkillGood skillGood = (SkillGood) redisTemplate.boundHashOps(SKILL_GOODS_PHONE).get(key);
            System.out.println(skillGood.getName() + "库存剩余：" + skillGood.getStockCount());
        }

    }

    public SkillGood queryProduct(Long productId) {
        return (SkillGood) redisTemplate.boundHashOps(SKILL_GOODS_PHONE).get(productId);
    }

    //更新商品信息
    public void update(SkillGood skillGood) {
        skillGoodRepository.save(skillGood);
    }

}
