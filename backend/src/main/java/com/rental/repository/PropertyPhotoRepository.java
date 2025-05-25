package com.rental.repository;

import com.rental.model.Property;
import com.rental.model.PropertyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyPhotoRepository extends JpaRepository<PropertyPhoto, Long> {
    List<PropertyPhoto> findByProperty(Property property);
}
