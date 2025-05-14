package com.rental.repository;

import com.rental.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // 根據價格範圍查詢
    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // 根據房間數查詢
    List<Property> findByBedroomsGreaterThanEqual(Integer minBedrooms);
    
    // 根據地址模糊查詢
    List<Property> findByAddressContaining(String address);
    
    // 根據擁有者ID查詢
    List<Property> findByOwnerId(Long ownerId);
    
    // 根據狀態查詢
    List<Property> findByStatus(String status);
    
    // 自定義查詢：綜合條件搜索
    @Query("SELECT p FROM Property p WHERE " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:bedrooms IS NULL OR p.bedrooms >= :bedrooms) AND " +
           "(:bathrooms IS NULL OR p.bathrooms >= :bathrooms) AND " +
           "(:type IS NULL OR p.type = :type) AND " +
           "(:address IS NULL OR p.address LIKE %:address%)")
    List<Property> searchProperties(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Integer bedrooms,
        Integer bathrooms,
        String type,
        String address
    );
} 