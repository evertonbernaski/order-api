package com.api.orderapi.services;

import com.api.orderapi.models.Order;
import com.api.orderapi.models.OrderStatus;
import com.api.orderapi.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findByOrderNumber(Long orderNumber) {

        return orderRepository.findById(orderNumber);
    }

    @Transactional
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public List<Order> findByOrderStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
}
