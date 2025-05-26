package com.rental.controller;

import com.rental.model.Landlord;
import com.rental.model.Property;
import com.rental.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import com.rental.model.PropertyPhoto;
import com.rental.repository.PropertyPhotoRepository;
import java.time.LocalDateTime;
import com.rental.repository.LandlordRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

@PostMapping(value = "/{propertyId}/uploadPhotos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> uploadPhotos(@PathVariable Long propertyId,
                                      @RequestParam("files") MultipartFile[] files) {
    try {
        Property property = propertyService.findById(propertyId).orElse(null);
        if (property == null) return ResponseEntity.notFound().build();

        for (MultipartFile file : files) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads/", filename);
            Files.copy(file.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            PropertyPhoto photo = new PropertyPhoto();
            photo.setProperty(property);
            photo.setPhotoUrl("/uploads/" + filename);
            photo.setPhotoDate(LocalDateTime.now());
            photo.setIsPrimary(false);

            propertyPhotoRepository.save(photo);
        }

        return ResponseEntity.ok("Photos uploaded successfully.");

    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Upload failed: " + e.getMessage());
    }
}


@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> createProperty(@Valid @RequestBody Property property,
                                        @RequestParam Long userId) {
    Landlord landlord = landlordRepository.findByUser_Id(userId)
        .orElseThrow(() -> new RuntimeException("找不到對應的房東"));

    property.setOwner(landlord);
    System.out.println("收到 userId: " + userId);
    return ResponseEntity.ok(propertyService.save(property));
}






    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        return propertyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @Valid @RequestBody Property property) {
        return propertyService.findById(id)
                .map(existingProperty -> {
                    property.setId(id);
                    return ResponseEntity.ok(propertyService.save(property));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        return propertyService.findById(id)
                .map(property -> {
                    propertyService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String address) {
        return ResponseEntity.ok(propertyService.searchProperties(
                minPrice, maxPrice, bedrooms, bathrooms, type, address));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Property>> getPropertiesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(propertyService.findByStatus(status));
    }

    @GetMapping("/by-price-range")
    public List<Property> getPropertiesByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return propertyService.findByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/by-bedrooms")
    public List<Property> getPropertiesByMinBedrooms(
            @RequestParam Integer minBedrooms) {
        return propertyService.findByMinBedrooms(minBedrooms);
    }

    @GetMapping("/by-address")
    public List<Property> getPropertiesByAddress(
            @RequestParam String address) {
        return propertyService.findByAddress(address);
    }

    @GetMapping("/by-owner/{ownerId}")
    public List<Property> getPropertiesByOwner(
            @PathVariable Long ownerId) {
        return propertyService.findByOwnerId(ownerId);
    }@Autowired
    private PropertyPhotoRepository propertyPhotoRepository;
    @Autowired
private LandlordRepository landlordRepository;



} 