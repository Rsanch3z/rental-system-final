package com.rental.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreatePropertyRequest {
    private String title;
    private String description;
    private String address;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private String type;
}
