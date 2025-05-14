package com.rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "標題不能為空")
    @Size(min = 5, max = 100, message = "標題長度必須在5-100字之間")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "描述不能為空")
    @Size(min = 10, max = 2000, message = "描述長度必須在10-2000字之間")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotBlank(message = "地址不能為空")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "價格不能為空")
    @DecimalMin(value = "0.0", message = "價格必須大於0")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "臥室數量不能為空")
    @Min(value = 0, message = "臥室數量不能小於0")
    @Column(nullable = false)
    private Integer bedrooms;

    @NotNull(message = "衛浴數量不能為空")
    @Min(value = 0, message = "衛浴數量不能小於0")
    @Column(nullable = false)
    private Integer bathrooms;

    @NotNull(message = "面積不能為空")
    @DecimalMin(value = "0.0", message = "面積必須大於0")
    @Column(nullable = false)
    private Double area;

    @NotNull(message = "類型不能為空")
    @Column(nullable = false)
    private String type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Viewing> viewings;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @NotNull(message = "狀態不能為空")
    @Column(nullable = false)
    private String status = "AVAILABLE";

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getter and Setter methods
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
} 