package com.backend.clothingstore.controllers;

import com.backend.clothingstore.DTO.OrderDTO;
import com.backend.clothingstore.DTO.OrderItemDTO;
import com.backend.clothingstore.errorHeandler.InsufficientStockException;
import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;
import com.backend.clothingstore.services.OrderService;
import com.backend.clothingstore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        try {
            Order createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(createdOrder);
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock for one or more items.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the order.");
        }
    }

    @GetMapping("/orderid/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + id + " not found.");
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update-order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable int id, @RequestBody @Valid OrderDTO orderDTO) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDTO);
            if (updatedOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + id + " not found.");
            }
            return ResponseEntity.ok(updatedOrder);
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock for one or more items.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the order.");
        }
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + id + " not found.");
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/add-items")
    public ResponseEntity<?> addItemToOrder(@PathVariable int orderId, @RequestBody @Valid OrderItemDTO orderItemDTO) {
        try {
            OrderItem createdOrderItem = orderService.addItemToOrder(orderId, orderItemDTO);
            if (createdOrderItem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
            }
            return ResponseEntity.ok(createdOrderItem);
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock for product ID " + orderItemDTO.getProductId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the item to the order.");
        }
    }

    @DeleteMapping("/remove-itm/orderid/{orderId}/remove-item/{itemId}")
    public ResponseEntity<String> removeItemFromOrder(@PathVariable int orderId, @PathVariable int itemId) {
        boolean isRemoved = orderService.removeItemFromOrder(orderId, itemId);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order item with ID " + itemId + " not found in order ID " + orderId + ".");
        }
        return ResponseEntity.noContent().build();
    }
}

