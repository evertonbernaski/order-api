package com.api.orderapi.services;

import com.api.orderapi.models.Order;
import com.api.orderapi.models.OrderStatus;
import com.api.orderapi.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@WebAppConfiguration
class OrderServiceTest {

    public static final long ORDER_NUMBER = 1L;
    public static final OrderStatus DELIVERED = OrderStatus.DELIVERED;
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Order order;

    @Mock
    private OrderStatus orderStatus;

    @Mock
    private Optional<Order> optionalOrder;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startOrder();
    }

    @Test
    void save() {
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Order response = orderService.save(order);
        assertNotNull(response);
        assertEquals(1L, response.getOrderNumber());
    }

    @Test
    void findAll() {
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> response = orderService.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());

    }

    @Test
    void findByOrderNumber() {
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(optionalOrder);

        Optional<Order> response = orderService.findByOrderNumber(ORDER_NUMBER);
        assertEquals(1L, response.get().getOrderNumber());
    }

    @Test
    void delete() {
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(optionalOrder);
        orderService.delete(order);
        Mockito.verify(orderRepository, Mockito.times(1)).delete(order);

    }

    @Test
    void findByOrderStatus() {
        Mockito.when(orderRepository.findByStatus(OrderStatus.DELIVERED)).thenReturn(Collections.singletonList(order));

        List<Order> response = orderService.findByOrderStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, response.get(0).getStatus());
    }

    private void startOrder() {
        order = new Order(ORDER_NUMBER, "Pastel de carne", DELIVERED, 5.0, 2, 20);
        optionalOrder = Optional.of(new Order(1L, "Pastel de carne", OrderStatus.DELIVERED, 5.0, 2, 20));
    }
}