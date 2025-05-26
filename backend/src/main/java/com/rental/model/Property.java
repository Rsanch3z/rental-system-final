package com.rental.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@ToString(exclude = {"owner", "reviews"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 100)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(min = 10, max = 2000)
    @Column(nullable = false, length = 1000)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer bedrooms;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer bathrooms;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "square_meters", nullable = false)
    private Double squareMeters;

    @NotNull
@Column(nullable = false)
private Integer area;

    @NotNull
    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "owner_id", nullable = false)
@JsonBackReference(value = "landlord-properties")
private Landlord owner;


 //   @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
//@JsonManagedReference(value = "property-viewings")
//private List<Viewing> viewings;

@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
@JsonManagedReference(value = "property-reviews")
private List<Review> reviews;

    @NotNull
    @Column(nullable = false)
    private String status = "AVAILABLE";

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
  @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference(value = "property-photos")
  private List<PropertyPhoto> photos = new ArrayList<>();


}
