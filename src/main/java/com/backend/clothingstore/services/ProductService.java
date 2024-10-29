package com.backend.clothingstore.services;

import com.backend.clothingstore.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    Product updateProduct(int id, Product productDetails);
    boolean deleteProduct(int id);
}
