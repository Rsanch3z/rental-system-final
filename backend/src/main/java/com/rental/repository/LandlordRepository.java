package com.rental.repository;

import com.rental.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    Optional<Landlord> findByUser_Id(Long userId);
}
