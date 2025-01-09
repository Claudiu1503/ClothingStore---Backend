package com.backend.clothingstore.services;

import com.backend.clothingstore.DTO.OrderDTO;
import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;
import com.backend.clothingstore.repositories.OrderRepository;
import com.backend.clothingstore.servicesImpl.OrderServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Rollback
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getOrderById() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1);
        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getId());
    }

    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> allOrders = orderService.getAllOrders();
        assertEquals(2, allOrders.size());
    }


    @Test
    void deleteOrder() {
        when(orderRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(orderRepository).deleteById(anyInt());

        boolean isDeleted = orderService.deleteOrder(1);
        assertTrue(isDeleted);
        verify(orderRepository, times(1)).deleteById(1);
    }




}
