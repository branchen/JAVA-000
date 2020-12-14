package com.week08.sharding.service;

import com.week08.sharding.common.OrderDTO;
import com.week08.sharding.common.enums.OrderStatus;
import com.week08.sharding.dao.IOrderDAO;
import com.week08.sharding.dao.entity.Order;
import com.week08.sharding.dao.entity.OrderItem;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    
    @Autowired
    private IOrderDAO orderDao;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderDTO orderDTO) {
        Order order = new Order(orderDTO);
        order.setCode(UUID.randomUUID().toString().replace("-", ""));
        orderDao.create(order, orderDTO.getItems().stream().map(OrderItem::new).collect(Collectors.toList()));
    }
    
    @Override
    public void updateOrderStatus(Long userId, Long orderId, OrderStatus orderStatus) {
        Order order = orderDao.getOrder(userId, orderId);
        if (Objects.isNull(order)) {
            throw new RuntimeException(String.format("订单不存在, 用户 ID: %s, 订单 ID: %s", userId, orderId));
        }
        orderDao.updateStatus(userId, orderId, orderStatus);
    }
    
    @Override
    public void prepareDAOResource() {
        orderDao.prepareDAOResource();
    }
    
    @Override
    public void destroyDAOResource() {
        orderDao.destroyDAOResource();
    }
    
    @Override
    public void deleteOrder(Long userId, Long orderId) {
        orderDao.deleteOrder(userId, orderId);
    }
    
    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> userOrders = orderDao.getUserOrders(userId);
        if (CollectionUtils.isEmpty(userOrders)) {
            return Collections.emptyList();
        }
        List<Long> orderIds = userOrders.stream().map(Order::getId).collect(Collectors.toList());
        List<OrderItem> userOrderItems = orderDao.getUserOrderItems(userId, orderIds);
        Map<Long, List<OrderItem>> orderItemMap = userOrderItems.stream().collect(Collectors.groupingBy(OrderItem::getOrderId));
        return userOrders.stream().map(order -> order.toOrderDTO(orderItemMap.get(order.getId()))).collect(Collectors.toList());
    }
}
