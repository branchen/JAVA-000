package com.week08.sharding.dao.entity;

import com.week08.sharding.common.OrderItemDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = -7132020259097546641L;
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
     * 修改时间
     */
    private Date gmtModified;
    
    public OrderItem() {
    }
    
    public OrderItem(OrderItemDTO orderItemDTO) {
        this.skuCode = orderItemDTO.getSkuCode();
        this.quantity = orderItemDTO.getQuantity();
        this.price = orderItemDTO.getPrice();
        this.itemAmount = orderItemDTO.getItemAmount();
        this.cutAmount = orderItemDTO.getCutAmount();
    }
    
    public OrderItemDTO toOrderItemDTO() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(id);
        orderItemDTO.setOrderId(orderId);
        orderItemDTO.setUserId(userId);
        orderItemDTO.setSkuCode(skuCode);
        orderItemDTO.setQuantity(quantity);
        orderItemDTO.setPrice(price);
        orderItemDTO.setItemAmount(itemAmount);
        orderItemDTO.setCutAmount(cutAmount);
        orderItemDTO.setGmtCreate(gmtCreate);
        orderItemDTO.setGmtModified(gmtModified);
        return orderItemDTO;
    }
    
}
