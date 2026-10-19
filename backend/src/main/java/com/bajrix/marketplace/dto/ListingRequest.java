package com.bajrix.marketplace.dto;

import com.bajrix.marketplace.entity.ListingStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ListingRequest(
        @NotNull Long productId,
        @NotNull @DecimalMin(value = "0.01") BigDecimal price,
        @NotNull @Min(0) Integer stockQuantity,
        @NotNull @Min(1) Integer minimumOrderQuantity,
        ListingStatus status
) {
}
