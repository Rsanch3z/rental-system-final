package com.rental.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_history")
    private String content;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "target_id")
    @JsonBackReference(value = "property-reviews")
    private Property property;
}
