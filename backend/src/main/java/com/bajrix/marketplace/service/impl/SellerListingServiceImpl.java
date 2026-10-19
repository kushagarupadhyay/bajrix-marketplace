package com.bajrix.marketplace.service.impl;

import com.bajrix.marketplace.dto.ListingRequest;
import com.bajrix.marketplace.dto.ListingResponse;
import com.bajrix.marketplace.dto.ListingUpdateRequest;
import com.bajrix.marketplace.entity.*;
import com.bajrix.marketplace.exception.BadRequestException;
import com.bajrix.marketplace.exception.ResourceNotFoundException;
import com.bajrix.marketplace.repository.ProductRepository;
import com.bajrix.marketplace.repository.SellerListingRepository;
import com.bajrix.marketplace.repository.SellerRepository;
import com.bajrix.marketplace.service.SellerListingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SellerListingServiceImpl implements SellerListingService {
    private final SellerListingRepository listings;
    private final ProductRepository products;
    private final SellerRepository sellers;

    public SellerListingServiceImpl(SellerListingRepository listings, ProductRepository products, SellerRepository sellers) {
        this.listings = listings;
        this.products = products;
        this.sellers = sellers;
    }

    @Transactional(readOnly = true)
    public List<ListingResponse> buyerOffers(Long productId) {
        if (!products.existsById(productId)) throw new ResourceNotFoundException("Product not found: " + productId);
        return listings.findBuyerOffers(productId, ListingStatus.ACTIVE, SellerStatus.APPROVED)
                .stream().map(ListingResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ListingResponse> myListings(Long sellerId) {
        Seller seller = requireSeller(sellerId);
        return listings.findBySellerIdOrderByUpdatedAtDesc(seller.getId())
                .stream().map(ListingResponse::from).toList();
    }

    @Transactional
    public ListingResponse create(Long sellerId, ListingRequest request) {
        Seller seller = requireSeller(sellerId);
        ensureApproved(seller);
        Product product = products.findById(request.productId())
                .filter(Product::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Active product not found: " + request.productId()));

        if (listings.existsByProductIdAndSellerId(product.getId(), seller.getId())) {
            throw new BadRequestException("This seller already has a listing for this product.");
        }
        validateQuantity(request.stockQuantity(), request.minimumOrderQuantity());
        SellerListing listing = new SellerListing(product, seller, request.price(),
                request.stockQuantity(), request.minimumOrderQuantity(),
                request.status() == null ? ListingStatus.ACTIVE : request.status());
        return ListingResponse.from(listings.save(listing));
    }

    @Transactional
    public ListingResponse update(Long sellerId, Long listingId, ListingUpdateRequest request) {
        Seller seller = requireSeller(sellerId);
        ensureApproved(seller);
        SellerListing listing = listings.findByIdAndSellerId(listingId, seller.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found for this seller: " + listingId));
        validateQuantity(request.stockQuantity(), request.minimumOrderQuantity());
        listing.update(request.price(), request.stockQuantity(), request.minimumOrderQuantity(), request.status());
        return ListingResponse.from(listings.save(listing));
    }

    @Transactional
    public ListingResponse changeStatus(Long sellerId, Long listingId, ListingStatus status) {
        Seller seller = requireSeller(sellerId);
        ensureApproved(seller);
        SellerListing listing = listings.findByIdAndSellerId(listingId, seller.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found for this seller: " + listingId));
        listing.update(listing.getPrice(), listing.getStockQuantity(), listing.getMinimumOrderQuantity(), status);
        return ListingResponse.from(listings.save(listing));
    }

    private Seller requireSeller(Long sellerId) {
        if (sellerId == null || sellerId < 1) throw new BadRequestException("A valid X-Seller-Id header is required.");
        return sellers.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found: " + sellerId));
    }

    private void ensureApproved(Seller seller) {
        if (seller.getStatus() != SellerStatus.APPROVED) {
            throw new BadRequestException("Only APPROVED sellers can manage listings.");
        }
    }

    private void validateQuantity(Integer stock, Integer minimum) {
        if (minimum > stock && stock > 0) {
            throw new BadRequestException("Minimum order quantity cannot exceed stock quantity.");
        }
        if (stock == 0 && minimum < 1) {
            throw new BadRequestException("Minimum order quantity must be at least 1.");
        }
    }
}
