package com.cqyc.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cqyc
 * @create 2023-10-25-15:30
 */
@Component
public class sentinelConfig {

    private static final String RULE_KEY = "hot";

    @PostConstruct
    private void init() {
        initDegradeRule();
        initFlowQpsRule();
        initSystemRule();
        initAuthorityRule();
        initParamFlowRule();
    }

    //熔断降级规则
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(RULE_KEY);
        //80s内调用接口出现 异常 ,次数超过5的时候, 进行熔断
        degradeRule.setCount(5);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setTimeWindow(80);
        rules.add(degradeRule);
        DegradeRuleManager.loadRules(rules);
    }

    //流量控制规则
    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule(RULE_KEY);
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }


    //系统保护规则
    private void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }

    //黑白名单控制
    private void initAuthorityRule(){
        List<AuthorityRule> rules=new ArrayList<>();

        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RULE_KEY);
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("nacos-consumer");
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);
    }

    //热点参数规则
    private void initParamFlowRule(){
        ParamFlowRule rule = new ParamFlowRule(RULE_KEY)
                .setParamIdx(0)
                .setCount(20);
        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf("4"))
                .setClassType(String.class.getName())
                .setCount(2);
        rule.setParamFlowItemList(Collections.singletonList(item));
        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }



}
