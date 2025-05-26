package com.rental.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long senderId;

    private Long receiverId;

    private Long propertyId;
} 
