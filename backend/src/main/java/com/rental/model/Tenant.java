package com.rental.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    private String email;
    private String name;
    private String password;
    private String phone;
}
