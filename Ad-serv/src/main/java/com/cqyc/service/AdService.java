package com.cqyc.service;

import com.cqyc.entity.AdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author cqyc
 * @create 2023-11-15-17:01
 */
@Service
public class AdService {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String AD_LIST = "AD_LIST";

    public List<AdEntity> getADList() {
        List<AdEntity> members = redisTemplate.boundHashOps(AD_LIST).values();
        return members;
    }

    public void modify(AdEntity adEntity) {
        redisTemplate.boundHashOps(AD_LIST).put(adEntity.getId(), adEntity);
    }

    public void delete(Long id) {
        redisTemplate.boundHashOps(AD_LIST).delete(id);
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void prepareGood() {
        Set keys = redisTemplate.boundHashOps(AD_LIST).keys();
        int i = 0;
        for (Object key : keys) {
            AdEntity entity = (AdEntity) redisTemplate.boundHashOps(AD_LIST).get(key);
            ++i;
            System.out.println("广告剩余： " + i);
        }
    }

}
