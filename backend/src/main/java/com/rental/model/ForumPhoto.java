package com.rental.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "forum_photos")
public class ForumPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Forum forum;

    private String photoUrl;

    private LocalDateTime photoDate = LocalDateTime.now();
}
