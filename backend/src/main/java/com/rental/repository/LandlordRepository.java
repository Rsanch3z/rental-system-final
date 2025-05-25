package com.rental.repository;

import com.rental.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
}
