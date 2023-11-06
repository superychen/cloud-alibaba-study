package com.cqyc.entity;

import java.io.Serializable;

/**
 * @author cqyc
 * @create 2023-11-03-14:53
 */
public class SkillEntity implements Serializable {
    private static final long serialVersionUID = 8915750801078132721L;

    private Long productId;

    private String userId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
