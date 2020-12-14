package com.week08.sharding.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderItemDTO implements Serializable {
    private static final long serialVersionUID = -4516563869527798368L;
    /**
     * ID
     */
    private Long id;
    /**
     * 订单 ID
     */
    private Long orderId;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 购买数量
     */
    private Integer quantity;
    /**
     * 商品结算价格
     */
    private Integer price;
    /**
     * 订单项小计
     */
    private Integer itemAmount;
    /**
     * 订单项减免金额
     */
    private Integer cutAmount;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
