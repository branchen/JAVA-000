package com.week08.sharding.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteOrderReqVo implements Serializable {
    private static final long serialVersionUID = 3821712223003455160L;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 订单 ID
     */
    private Long orderId;
}
