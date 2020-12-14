package com.week08.sharding.dao;

import com.week08.sharding.common.enums.OrderStatus;
import com.week08.sharding.dao.entity.Order;
import com.week08.sharding.dao.entity.OrderItem;

import java.util.List;

public interface IOrderDAO {
    
    /**
     * 创建订单
     *
     * @param order 订单信息
     */
    void create(Order order, List<OrderItem> orderItems);
    
    /**
     * 准备 DAO Resource
     */
    void prepareDAOResource();
    
    /**
     * 销毁 DAO Resource
     */
    void destroyDAOResource();
    
    /**
     * 更改订单状态
     *
     * @param userId      用户 ID
     * @param orderId     订单 ID
     * @param orderStatus 订单状态
     */
    void updateStatus(Long userId, Long orderId, OrderStatus orderStatus);
    
    /**
     * 获取订单
     *
     * @param userId  用户 ID
     * @param orderId 订单 ID
     * @return 订单信息
     */
    Order getOrder(Long userId, Long orderId);
    
    /**
     * 删除订单
     *
     * @param userId  用户 ID
     * @param orderId 订单 ID
     */
    void deleteOrder(Long userId, Long orderId);
    
    /**
     * 获取用户订单信息集合
     *
     * @param userId 用户 ID
     * @return 订单信息集合
     */
    List<Order> getUserOrders(Long userId);
    
    /**
     * 获取用户订单项
     *
     * @param userId   用户 ID
     * @param orderIds 订单 ID 集合
     * @return 用户订单项集合
     */
    List<OrderItem> getUserOrderItems(Long userId, List<Long> orderIds);
}
