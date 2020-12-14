package com.week08.sharding.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author David Liu
 */
@AllArgsConstructor
@Getter
public enum OrderStatus {
    CANCEL(-1, "已取消"),
    CREATED(0, "已创建");
    
    private final int val;
    
    private final String description;
}
