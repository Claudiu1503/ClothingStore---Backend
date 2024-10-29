package com.backend.clothingstore.repositories;

import com.backend.clothingstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
