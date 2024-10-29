package com.backend.clothingstore.controllers;

import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;
import com.backend.clothingstore.services.OrderService;
import com.backend.clothingstore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update-order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody @Valid Order orderDetails) {
        Order updatedOrder = orderService.updateOrder(id, orderDetails);
        if (updatedOrder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/add-items")
    public ResponseEntity<OrderItem> addItemToOrder(@PathVariable int orderId, @RequestBody @Valid OrderItem orderItem) {
        OrderItem createdOrderItem = orderService.addItemToOrder(orderId, orderItem);
        if (createdOrderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createdOrderItem);
    }

    @DeleteMapping("/{orderId}/remove-item/{itemId}")
    public ResponseEntity<Void> removeItemFromOrder(@PathVariable int orderId, @PathVariable int itemId) {
        boolean isRemoved = orderService.removeItemFromOrder(orderId, itemId);
        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


}
