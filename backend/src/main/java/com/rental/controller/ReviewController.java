package com.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.rental.dto.ReviewRequest;

import java.util.Date;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest request) {
        String sql = "INSERT INTO reviews (target_type, target_id, reviewer_id, reviewer_type, review_history, review_date, rating, property_id, user_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update(sql,
                request.targetType,
                request.targetId,
                request.reviewerId,
                request.reviewerType,
                request.comment,
                new Date(),
                request.rating,
                request.propertyId,
                request.userId
        );

        if (result > 0) {
            return ResponseEntity.ok("評價成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("評價失敗");
        }
    }
}
