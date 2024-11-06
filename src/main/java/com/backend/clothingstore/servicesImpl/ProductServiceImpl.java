package com.backend.clothingstore.servicesImpl;

import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.repositories.ProductRepository;
import com.backend.clothingstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(int id, Product productDetails) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            if (productDetails.getName() != null) {
                product.setName(productDetails.getName());
            }
            if (productDetails.getCategory() != null) {
                product.setCategory(productDetails.getCategory());
            }
            if (productDetails.getPrice() != null) {
                product.setPrice(productDetails.getPrice());
            }
            if (productDetails.getQuantity() != -1) {
                product.setQuantity(productDetails.getQuantity());
            }
            if (productDetails.getLongDescription() != null) {
                product.setLongDescription(productDetails.getLongDescription());
            }
            if (productDetails.getShortDescription() != null) {
                product.setShortDescription(productDetails.getShortDescription());
            }
            if (productDetails.getGender() != null) {
                product.setGender(productDetails.getGender());
            }
            if (productDetails.getColor() != null) {
                product.setColor(productDetails.getColor());
            }
            if(productDetails.getBrand() != null) {
                product.setBrand(productDetails.getBrand());
            }


            return productRepository.save(product);
        }
        return null;
    }


    @Override
    public boolean deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
