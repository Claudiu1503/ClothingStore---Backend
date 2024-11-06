package com.backend.clothingstore.services;

import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.repositories.ProductRepository;
import com.backend.clothingstore.servicesImpl.ProductServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("T-shirt");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);
        assertEquals("T-shirt", createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId(1);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1);
        assertNotNull(foundProduct);
        assertEquals(1, foundProduct.getId());
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> allProducts = productService.getAllProducts();
        assertEquals(2, allProducts.size());
    }

    @Test
    void updateProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("T-shirt");

        Product updatedProductDetails = new Product();
        updatedProductDetails.setName("Updated T-shirt");

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProductDetails);

        Product updatedProduct = productService.updateProduct(1, updatedProductDetails);
        assertNotNull(updatedProduct);
        assertEquals("Updated T-shirt", updatedProduct.getName());
    }

    @Test
    void deleteProduct() {
        when(productRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyInt());

        boolean isDeleted = productService.deleteProduct(1);
        assertTrue(isDeleted);
        verify(productRepository, times(1)).deleteById(1);
    }
}
