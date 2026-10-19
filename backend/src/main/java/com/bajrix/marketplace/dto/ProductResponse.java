package com.bajrix.marketplace.dto;

import com.bajrix.marketplace.entity.Product;

public record ProductResponse(
        Long id, String name, String brand, String category,
        String unit, String description, boolean active
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getBrand(), p.getCategory(),
                p.getUnit(), p.getDescription(), p.isActive());
    }
}
