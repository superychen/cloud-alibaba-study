package com.cqyc.dao;

import com.cqyc.entity.SkillOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cqyc
 * @create 2023-11-02-17:45
 */
@Repository
public interface SkillOrderRepository extends JpaRepository<SkillOrder, Long> {




}
