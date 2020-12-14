package com.week08.sharding.dao.mapper;

import com.week08.sharding.common.enums.OrderStatus;
import com.week08.sharding.dao.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {
    
    /**
     * 创建订单
     *
     * @param order 订单信息
     * @return 影响行数
     */
    int create(@Param("order") Order order);
    
    /**
     * 创建表
     */
    void createTableIfNotExists();
    
    /**
     * 删除表
     */
    void dropTable();
    
    /**
     * 更新订单状态
     *
     * @param userId      用户 ID
     * @param orderId     订单 ID
     * @param orderStatus 订单状态
     * @return 影响行数
     */
    @Update("UPDATE `order` SET `status` = #{status.val} WHERE `user_id` = #{userId} AND `id` = #{orderId}")
    int updateStatus(@Param("userId") Long userId, @Param("orderId") Long orderId, @Param("status") OrderStatus orderStatus);
    
    /**
     * 获取订单
     *
     * @param userId  用户 ID
     * @param orderId 订单 ID
     * @return 订单信息
     */
    Order get(@Param("userId") Long userId, @Param("orderId") Long orderId);
    
    /**
     * 删除订单
     *
     * @param userId  用户 ID
     * @param orderId 订单 ID
     */
    @Delete("DELETE FROM `order` WHERE `user_id` = #{userId} AND `id` = #{orderId}")
    void delete(@Param("userId") Long userId, @Param("orderId") Long orderId);
    
    /**
     * 获取用户订单信息
     *
     * @param userId 用户 ID
     * @return 订单信息集合
     */
    List<Order> listByUserId(@Param("userId") Long userId);
}
