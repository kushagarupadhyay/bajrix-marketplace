package com.bajrix.marketplace.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_category", columnList = "category")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(length = 100)
    private String brand;

    @Column(length = 80)
    private String category;

    @Column(nullable = false, length = 40)
    private String unit;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Product() {
    }

    public Product(String name, String brand, String category, String unit, String description) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.unit = unit;
        this.description = description;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void update(String name, String brand, String category, String unit, String description, boolean active) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.unit = unit;
        this.description = description;
        this.active = active;
    }
}
