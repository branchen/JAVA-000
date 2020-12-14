package com.week08.sharding.dao;

import com.week08.sharding.common.enums.OrderStatus;
import com.week08.sharding.dao.entity.Order;
import com.week08.sharding.dao.entity.OrderItem;
import com.week08.sharding.dao.mapper.OrderItemMapper;
import com.week08.sharding.dao.mapper.OrderMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class OrderDAO implements IOrderDAO {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    
    @Override
    public void create(Order order, List<OrderItem> orderItems) {
        orderMapper.create(order);
        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(order.getId());
            orderItem.setUserId(order.getUserId());
        });
        orderItemMapper.create(orderItems);
    }
    
    @Override
    public void prepareDAOResource() {
        orderMapper.createTableIfNotExists();
        orderItemMapper.createTableIfNotExists();
    }
    
    @Override
    public void destroyDAOResource() {
        orderMapper.dropTable();
        orderItemMapper.dropTable();
    }
    
    @Override
    public void updateStatus(Long userId, Long orderId, OrderStatus orderStatus) {
        orderMapper.updateStatus(userId, orderId, orderStatus);
    }
    
    @Override
    public Order getOrder(Long userId, Long orderId) {
        return orderMapper.get(userId, orderId);
    }
    
    @Override
    public void deleteOrder(Long userId, Long orderId) {
        orderMapper.delete(userId, orderId);
    }
    
    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderMapper.listByUserId(userId);
    }
    
    @Override
    public List<OrderItem> getUserOrderItems(Long userId, List<Long> orderIds) {
        return orderItemMapper.listUserOrderItems(userId, orderIds);
    }
}
