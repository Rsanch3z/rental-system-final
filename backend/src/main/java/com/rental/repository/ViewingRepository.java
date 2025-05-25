package com.rental.repository;

import com.rental.model.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViewingRepository extends JpaRepository<Viewing, Long> {
    List<Viewing> findByPropertyId(Long propertyId);
    List<Viewing> findByUser_Id(Long userId); // ✅ 改這裡
    List<Viewing> findByPropertyIdAndScheduledTimeBetween(Long propertyId, LocalDateTime start, LocalDateTime end);
    List<Viewing> findByStatus(String status);
}
