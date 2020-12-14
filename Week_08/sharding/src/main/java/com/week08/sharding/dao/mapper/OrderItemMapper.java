package com.week08.sharding.dao.mapper;

import com.week08.sharding.dao.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    
    /**
     * 创建订单项
     *
     * @param orderItems 订单项列表
     * @return 创建记录条数
     */
    int create(@Param("orderItems") List<OrderItem> orderItems);
    
    /**
     * 创建表资源
     */
    void createTableIfNotExists();
    
    /**
     * 销毁资源
     */
    void dropTable();
    
    /**
     * 获取用户订单项集合
     *
     * @param userId   用户 ID
     * @param orderIds 订单 ID 集合
     * @return 用户订单项集合
     */
    List<OrderItem> listUserOrderItems(@Param("userId") Long userId, @Param("orderIds") List<Long> orderIds);
}

