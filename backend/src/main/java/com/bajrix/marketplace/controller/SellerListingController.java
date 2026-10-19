package com.bajrix.marketplace.controller;

import com.bajrix.marketplace.dto.ListingRequest;
import com.bajrix.marketplace.dto.ListingResponse;
import com.bajrix.marketplace.dto.ListingUpdateRequest;
import com.bajrix.marketplace.entity.ListingStatus;
import com.bajrix.marketplace.service.SellerListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SellerListingController {
    private final SellerListingService listingService;

    public SellerListingController(SellerListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/api/products/{productId}/offers")
    public List<ListingResponse> offers(@PathVariable Long productId) {
        return listingService.buyerOffers(productId);
    }

    @GetMapping("/api/seller/listings")
    public List<ListingResponse> myListings(@RequestHeader("X-Seller-Id") Long sellerId) {
        return listingService.myListings(sellerId);
    }

    @PostMapping("/api/seller/listings")
    @ResponseStatus(HttpStatus.CREATED)
    public ListingResponse create(@RequestHeader("X-Seller-Id") Long sellerId,
                                  @Valid @RequestBody ListingRequest request) {
        return listingService.create(sellerId, request);
    }

    @PutMapping("/api/seller/listings/{listingId}")
    public ListingResponse update(@RequestHeader("X-Seller-Id") Long sellerId,
                                  @PathVariable Long listingId,
                                  @Valid @RequestBody ListingUpdateRequest request) {
        return listingService.update(sellerId, listingId, request);
    }

    @PatchMapping("/api/seller/listings/{listingId}/status")
    public ListingResponse changeStatus(@RequestHeader("X-Seller-Id") Long sellerId,
                                        @PathVariable Long listingId,
                                        @RequestParam ListingStatus status) {
        return listingService.changeStatus(sellerId, listingId, status);
    }
}
