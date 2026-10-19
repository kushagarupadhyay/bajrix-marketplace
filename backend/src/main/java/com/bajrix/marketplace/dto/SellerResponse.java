package com.bajrix.marketplace.dto;

import com.bajrix.marketplace.entity.Seller;

public record SellerResponse(Long id, String name, String email, String city, String status) {
    public static SellerResponse from(Seller s) {
        return new SellerResponse(s.getId(), s.getName(), s.getEmail(), s.getCity(), s.getStatus().name());
    }
}
