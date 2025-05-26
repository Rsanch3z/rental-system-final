package com.rental.dto;

public class ReviewRequest {
    public String targetType; // "house", "landlord", or "tenant"
    public Long targetId;
    public Long reviewerId;
    public String reviewerType; // "tenant" or "landlord"
    public String comment;
    public int rating;
    public Long propertyId;
    public Long userId;
}