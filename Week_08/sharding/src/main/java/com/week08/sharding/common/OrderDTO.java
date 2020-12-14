package com.week08.sharding.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -7018407590214245328L;
    /**
     * 订单 ID
     */
    private Long id;
    /**
     * 订单编码
     */
    private String code;
    /**
     * 商家 ID
     */
    private Long merchantId;
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 订单金额
     */
    private Integer amount;
    /**
     * 减免总金额
     */
    private Integer cutAmount;
    /**
     * 订单配送地址 ID
     */
    private Long shippingAddressId;
    /**
     * 订单明细信息
     */
    private List<OrderItemDTO> items;
    /**
     * 订单状态
     */
    private int status;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新信息
     */
    private Date gmtModified;
}
