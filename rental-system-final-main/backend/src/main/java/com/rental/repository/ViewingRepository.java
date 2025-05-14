package com.rental.repository;

import com.rental.model.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViewingRepository extends JpaRepository<Viewing, Long> {
    List<Viewing> findByPropertyId(Long propertyId);
    List<Viewing> findByUserId(Long userId);
    List<Viewing> findByPropertyIdAndViewingTimeBetween(Long propertyId, LocalDateTime start, LocalDateTime end);
    List<Viewing> findByStatus(String status);
} 