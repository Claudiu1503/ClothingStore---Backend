package com.backend.clothingstore.repositories;

import com.backend.clothingstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
