package com.rental.service.impl;

import com.rental.model.Property;
import com.rental.repository.PropertyRepository;
import com.rental.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> findById(Long id) {
        return propertyRepository.findById(id);
    }

    @Override
    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public List<Property> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return propertyRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Property> findByMinBedrooms(Integer minBedrooms) {
        return propertyRepository.findByBedroomsGreaterThanEqual(minBedrooms);
    }

    @Override
    public List<Property> findByAddress(String address) {
        return propertyRepository.findByAddressContaining(address);
    }

    @Override
    public List<Property> findByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Property> findByStatus(String status) {
        return propertyRepository.findByStatus(status);
    }

    @Override
    public List<Property> searchProperties(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer bedrooms,
            Integer bathrooms,
            String type,
            String address) {
        return propertyRepository.searchProperties(minPrice, maxPrice, bedrooms, bathrooms, type, address);
    }
} 