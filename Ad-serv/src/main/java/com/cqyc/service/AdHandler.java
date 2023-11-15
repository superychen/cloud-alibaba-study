package com.cqyc.service;

import com.cqyc.entity.AdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * @author cqyc
 * @create 2023-11-15-16:13
 * 广告服务-canal监听广告的实体，数据库发生变化就会处理。
 */
@Component
public class AdHandler implements EntryHandler<AdEntity> {

    @Autowired
    private AdService adService;

    @Override
    public void insert(AdEntity adEntity) {
        System.out.println(adEntity.getName());
        adService.modify(adEntity);
    }

    @Override
    public void update(AdEntity before, AdEntity after) {
        adService.modify(after);
    }

    @Override
    public void delete(AdEntity adEntity) {
        adService.delete(adEntity.getId());
    }
}
