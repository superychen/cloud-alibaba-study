package com.cqyc;

import com.alibaba.nacos.api.cmdb.pojo.Entity;
import com.alibaba.nacos.api.cmdb.pojo.EntityEvent;
import com.alibaba.nacos.api.cmdb.pojo.Label;
import com.alibaba.nacos.api.cmdb.pojo.PreservedEntityTypes;
import com.alibaba.nacos.api.cmdb.spi.CmdbService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cqyc
 * @create 2023-10-23-18:09
 * mvn package assembly:single -Dmaven.test.skip=true
 */
public class CmdbDemo implements CmdbService {

    private Map<String, Map<String, Entity>> entityMap = new ConcurrentHashMap<>();

    private Map<String, Label> labelMap = new ConcurrentHashMap<>();

    public CmdbDemo() {
        Label label = new Label();
        label.setName("cluster");
        Set<String> values = new HashSet<>();
        values.add("BEIJING");
        values.add("HANGZHOU");
        label.setValues(values);
        labelMap.put(label.getName(), label);

        //先赋空值
        entityMap.put(PreservedEntityTypes.ip.name(), new HashMap<String, Entity>());

        //给253这台机器来一个北京的集群
        Entity entity = new Entity();
        entity.setName("192.168.50.253");
        entity.setType(PreservedEntityTypes.ip.name());
        Map<String, String> labels = new HashMap<>();
        labels.put("cluster", "BEIJING");
        entity.setLabels(labels);
        entityMap.get(PreservedEntityTypes.ip.name()).put(entity.getName(), entity);


        //给100这个机器杭州的集群
        Entity entity2 = new Entity();
        entity2.setName("192.168.50.100");
        entity2.setType(PreservedEntityTypes.ip.name());
        Map<String, String> labels2 = new HashMap<>();
        labels2.put("cluster", "HANGZHOU");
        entity2.setLabels(labels2);
        entityMap.get(PreservedEntityTypes.ip.name()).put(entity2.getName(), entity2);


    }

    @Override
    public Set<String> getLabelNames() {
        Set<String> set = new HashSet<>();
        set.add("cluster");
        return set;
    }

    @Override
    public Set<String> getEntityTypes() {
        Set<String> set = new HashSet<>();
        set.add(PreservedEntityTypes.ip.name());
        return set;
    }

    @Override
    public Label getLabel(String s) {
        return labelMap.get(s);
    }

    @Override
    public String getLabelValue(String entityName, String entityType, String labelName) {
        return entityMap.get(entityType).get(entityName).getLabels().get(labelName);
    }

    @Override
    public Map<String, String> getLabelValues(String entityName, String entityType) {
        return entityMap.get(entityType).get(entityName).getLabels();
    }

    @Override
    public Map<String, Map<String, Entity>> getAllEntities() {
        return entityMap;
    }

    @Override
    public List<EntityEvent> getEntityEvents(long l) {
        return null;
    }

    @Override
    public Entity getEntity(String entityName, String entityType ) {
        return entityMap.get(entityType).get(entityName);
    }
}
