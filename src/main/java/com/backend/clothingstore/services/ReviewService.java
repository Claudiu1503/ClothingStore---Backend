package com.backend.clothingstore.services;


import com.backend.clothingstore.model.Review;
import com.backend.clothingstore.model.User;

import java.util.List;

public interface ReviewService {
    public Review addReview(User user, Long productId, String content, int rating);

    List<Review> getReviewsForProduct(Long productId);

    void deleteReview(Long id);
}
