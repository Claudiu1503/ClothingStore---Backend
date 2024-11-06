package com.backend.clothingstore.servicesImpl;

import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.model.Review;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.repositories.ProductRepository;
import com.backend.clothingstore.repositories.ReviewRepository;
import com.backend.clothingstore.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    public Review addReview(User user,Long productId, String content, int rating) {

        // Find the product by ID
        Product product = productRepository.findById(Math.toIntExact(productId))
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Create and save the review
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setContent(content);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}
