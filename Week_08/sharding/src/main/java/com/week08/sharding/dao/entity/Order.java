package com.week08.sharding.dao.entity;

import com.week08.sharding.common.OrderDTO;
import com.week08.sharding.common.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Order implements Serializable {
    
    private static final long serialVersionUID = 9146004833535159719L;
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
     * 订单状态
     */
    private int status;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
    
    public Order() {
    }
    
    public Order(OrderDTO orderDTO) {
        this.merchantId = orderDTO.getMerchantId();
        this.userId = orderDTO.getUserId();
        this.amount = orderDTO.getAmount();
        this.cutAmount = orderDTO.getCutAmount();
        this.shippingAddressId = orderDTO.getShippingAddressId();
        this.status = OrderStatus.CREATED.getVal();
    }
    
    public OrderDTO toOrderDTO(List<OrderItem> orderItems) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setCode(code);
        orderDTO.setMerchantId(merchantId);
        orderDTO.setUserId(userId);
        orderDTO.setAmount(amount);
        orderDTO.setCutAmount(cutAmount);
        orderDTO.setShippingAddressId(shippingAddressId);
        orderDTO.setStatus(status);
        orderDTO.setGmtCreate(gmtCreate);
        orderDTO.setGmtModified(gmtModified);
        orderDTO.setItems(orderItems.stream().map(OrderItem::toOrderItemDTO).collect(Collectors.toList()));
        return orderDTO;
    }
}
