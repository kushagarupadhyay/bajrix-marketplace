package com.bajrix.marketplace.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "seller_listings",
        uniqueConstraints = @UniqueConstraint(name = "uk_listing_product_seller", columnNames = {"product_id", "seller_id"}),
        indexes = {
                @Index(name = "idx_listing_product_status", columnList = "product_id,status"),
                @Index(name = "idx_listing_seller", columnList = "seller_id")
        })
public class SellerListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer minimumOrderQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ListingStatus status = ListingStatus.ACTIVE;

    @Version
    private Long version;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    protected SellerListing() {
    }

    public SellerListing(Product product, Seller seller, BigDecimal price, Integer stockQuantity,
                         Integer minimumOrderQuantity, ListingStatus status) {
        this.product = product;
        this.seller = seller;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.status = status == null ? ListingStatus.ACTIVE : status;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public Integer getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public Long getVersion() {
        return version;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void update(BigDecimal price, Integer stockQuantity, Integer minimumOrderQuantity, ListingStatus status) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
