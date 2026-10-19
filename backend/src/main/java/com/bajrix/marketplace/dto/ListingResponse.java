package com.bajrix.marketplace.dto;

import com.bajrix.marketplace.entity.SellerListing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListingResponse(Long id, Long productId, String productName, String productUnit, Long sellerId,
                              String sellerName, String sellerCity, BigDecimal price, Integer stockQuantity,
                              Integer minimumOrderQuantity, String status, Long version, LocalDateTime updatedAt) {
    public static ListingResponse from(SellerListing l) {
        return new ListingResponse(l.getId(), l.getProduct().getId(), l.getProduct().getName(), l.getProduct().getUnit(),
                                    l.getSeller().getId(), l.getSeller().getName(), l.getSeller().getCity(), l.getPrice(),
                                     l.getStockQuantity(), l.getMinimumOrderQuantity(), l.getStatus().name(), l.getVersion(), l.getUpdatedAt());
    }
}
