package com.backend.clothingstore.controllers;

import com.backend.clothingstore.model.Review;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public Review addReview(@RequestParam User user, @RequestParam Long productId, @RequestParam String content, @RequestParam int rating) {
        return reviewService.addReview(user,productId, content, rating);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewsForProduct(@PathVariable Long productId) {
        return reviewService.getReviewsForProduct(productId);
    }

    @DeleteMapping( "/delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

}
