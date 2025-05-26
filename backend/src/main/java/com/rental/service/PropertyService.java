package com.rental.service;

import com.rental.model.Property;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PropertyService {
    Property save(Property property);
    Optional<Property> findById(Long id);
    List<Property> findAll();
    void deleteById(Long id);
    List<Property> findByStatus(String status);
    List<Property> searchProperties(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Integer bedrooms,
        Integer bathrooms,
        String type,
        String address
    );
    
    // 新增的方法
    List<Property> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Property> findByMinBedrooms(Integer minBedrooms);
    List<Property> findByAddress(String address);
    List<Property> findByOwnerId(Long ownerId);
} 